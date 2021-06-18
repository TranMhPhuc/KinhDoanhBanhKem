package control.product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import model.ingredient.IngredientModelInterface;
import model.product.ProductManageModelInterface;
import model.product.ProductModel;
import model.product.ProductModelInterface;
import model.product.ProductSize;
import model.product.ingredientDetail.IngredientDetailModel;
import model.product.ingredientDetail.IngredientDetailModelInterface;
import org.junit.Assert;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import util.excel.ExcelTransfer;
import view.product.IngredientEditDialog;
import view.product.ProductPanel;
import view.product.ProductProduceDialog;

public class ProductController implements ProductControllerInterface {

    private static final String SP_CHECK_DELETE_PRODUCT_CONDITION
            = "{? = call check_if_product_exists_in_any_bill(?)}";

    private static final String SP_CHECK_PRODUCE_PRODUCT_CONDITION
            = "{? = call check_if_ingredient_amount_enough_to_produce_product(?, ?, ?)}";

    private static Connection dbConnection;

    private List<ProductModelInterface> searchList;

    private ProductManageModelInterface productManageModel;
    private ProductPanel productPanel;

    private IngredientEditDialog dialogIngredientEditing;
    private ProductProduceDialog productProduceDialog;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    public ProductController(ProductManageModelInterface model) {
        this.searchList = new ArrayList<>();
        this.productManageModel = model;
    }

    @Override
    public void setProductPanelView(ProductPanel productPanel) {
        if (productPanel == null) {
            throw new NullPointerException();
        }
        this.productPanel = productPanel;
        productPanel.setProductController(this);
        productPanel.setProductManageModel(productManageModel);
    }

    private boolean isProductNameInputValid(String productName) {
        if (productName.isEmpty()) {
            productPanel.showErrorMessage("Product name is required.");
            return false;
        }
        return true;
    }

    /**
     * Check if same name but not size
     *
     * @param productName
     * @param productSize
     * @return
     */
    private boolean isProductNameAndSizeInputVallid(String productName, String productSize) {
        Iterator<ProductModelInterface> iterator = this.productManageModel.getAllProductData();
        while (iterator.hasNext()) {
            ProductModelInterface element = iterator.next();
            if (element.getName().equals(productName)
                    && element.getSize().equals(productSize)) {
                productPanel.showErrorMessage("Product with same name can't have same size value.");
                return false;
            }
        }

        return true;
    }

    private boolean isProductCostInputVallid(long productCost) {
        if (productCost <= 0) {
            productPanel.showErrorMessage("Product cost is not negative.");
            return false;
        }
        return true;
    }

    private boolean isProductPriceInputVallid(long productPrice, long productCost) {
        if (productPrice <= 0) {
            productPanel.showErrorMessage("Product cost is not negative.");
            return false;
        }

        if (productPrice < 1.1 * productCost) {
            productPanel.showErrorMessage("Product price must be greater at least 10% than cost.");
            return false;
        }
        return true;
    }

    private boolean isProductCostAndPriceVallid(String productName, int preSize, int nextSize, long productCost, long productPrice) {
        ProductSize[] productSizes = ProductSize.values();

        if (preSize != -1) {
            ProductModelInterface preSizeProduct = this.productManageModel
                    .getProductByNameAndSize(productName, productSizes[preSize].name());
            if (preSizeProduct != null) {
                if (preSizeProduct.getCost() > productCost) {
                    productPanel.showErrorMessage("Product cost is smaller than one having same name and less size "
                            + productSizes[preSize].name() + " (" + preSizeProduct.getCost()
                            + " > " + productCost + ").");
                    return false;
                }

                if (preSizeProduct.getPrice() > productPrice) {
                    productPanel.showErrorMessage("Product price is smaller than one having same name and less size "
                            + productSizes[preSize].name() + " (" + preSizeProduct.getPrice()
                            + " > " + productPrice + ").");
                    return false;
                }
            }
        }

        if (nextSize != -1) {
            ProductModelInterface nextSizeProduct = this.productManageModel
                    .getProductByNameAndSize(productName, productSizes[nextSize].name());
            if (nextSizeProduct != null) {
                if (nextSizeProduct.getCost() < productCost) {
                    productPanel.showErrorMessage("Product cost is greater than one having same name and larger size "
                            + productSizes[nextSize].name() + " (" + nextSizeProduct.getCost()
                            + " < " + productCost + ").");
                    return false;
                }

                if (nextSizeProduct.getPrice() < productPrice) {
                    productPanel.showErrorMessage("Product price is greater than one having same name and larger size "
                            + productSizes[nextSize].name() + " (" + nextSizeProduct.getPrice()
                            + " < " + productPrice + ").");
                    return false;
                }

            }
        }

        return true;
    }

    private boolean isProductCanDelete(ProductModelInterface product) {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_CHECK_DELETE_PRODUCT_CONDITION);
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            product.setKeyArg(2, ProductModel.ID_HEADER, callableStatement);

            callableStatement.execute();

            boolean canDelete = callableStatement.getBoolean(1);

            if (!canDelete) {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public void requestCreateProduct() {
        String productIDText = this.productPanel.getProductIDText();

        String productNameInput = this.productPanel.getProductName();

        if (!isProductNameInputValid(productNameInput)) {
            return;
        }

        // Check product size
        String productSize = this.productPanel.getProductSize();

        if (!isProductNameAndSizeInputVallid(productNameInput, productSize)) {
            return;
        }

        // Check product cost
        String productCostInput = this.productPanel.getProductCost();

        if (productCostInput.isEmpty()) {
            productPanel.showErrorMessage("Product cost is required.");
            return;
        }

        long productCost = 0;
        try {
            productCost = Long.parseLong(productCostInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage("Product cost is invallid.");
            return;
        }

        if (!isProductCostInputVallid(productCost)) {
            return;
        }

        // Check product price
        String productPriceInput = this.productPanel.getProductPrice();

        if (productPriceInput.isEmpty()) {
            productPanel.showErrorMessage("Product price is required.");
            return;
        }

        long productPrice = 0;
        try {
            productPrice = Long.parseLong(productPriceInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage("Product price is invallid.");
            return;
        }

        if (!isProductPriceInputVallid(productPrice, productCost)) {
            return;
        }

        ProductSize[] productSizes = ProductSize.values();

        int preSize = -1, nextSize = -1;
        for (int i = 0; i < productSizes.length; i++) {
            if (productSizes[i].name().equals(productSize)) {
                if (i > 0) {
                    preSize = i - 1;
                }
                if (i < productSizes.length - 1) {
                    nextSize = i + 1;
                }
                break;
            }
        }

        if (!isProductCostAndPriceVallid(productSize, preSize, nextSize, productCost, productPrice)) {
            return;
        }

        if (productManageModel.getIngredientDetailBufferList().isEmpty()) {
            this.productPanel.showErrorMessage("Ingredient list must have at least one ingredient.");
            return;
        }

        ProductModelInterface product = new ProductModel();
        product.setProductID(productIDText);
        product.setName(productNameInput);
        product.setSize(ProductSize.getProductSizeFromString(productSize));
        product.setCost(productCost);
        product.setPrice(productPrice);

        this.productManageModel.addProduct(product);

        productManageModel.getIngredientDetailBufferList().forEach(ingredientDetail -> {
            ingredientDetail.setProduct(product);
            product.addIngredientDetail(ingredientDetail);
        });

        this.productPanel.exitEditState();
        productPanel.showInfoMessage("Insert new product successfully.");
    }

    @Override
    public void requestUpdateProduct() {
        String productIDText = this.productPanel.getProductIDText();

        ProductModelInterface product = this.productManageModel.getProductByID(productIDText);

        Assert.assertNotNull(product);

        String productNameInput = this.productPanel.getProductName();

        if (!isProductNameInputValid(productNameInput)) {
            return;
        }

        String productSize = this.productPanel.getProductSize();

        if (!product.getName().equals(productNameInput)
                || !product.getSize().equals(productSize)) {
            if (!isProductNameAndSizeInputVallid(productNameInput, productSize)) {
                return;
            }
        }

        String productCostInput = this.productPanel.getProductCost();

        if (productCostInput.isEmpty()) {
            productPanel.showErrorMessage("Product cost is required.");
            return;
        }

        long productCost = 0;
        try {
            productCost = Long.parseLong(productCostInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage("Product cost is invallid.");
            return;
        }

        if (!isProductCostInputVallid(productCost)) {
            return;
        }

        String productPriceInput = this.productPanel.getProductPrice();

        if (productPriceInput.isEmpty()) {
            productPanel.showErrorMessage("Product price is required.");
            return;
        }

        long productPrice = 0;
        try {
            productPrice = Long.parseLong(productPriceInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage("Product price is invallid.");
            return;
        }

        if (!isProductPriceInputVallid(productPrice, productCost)) {
            return;
        }

        ProductSize[] productSizes = ProductSize.values();

        int preSize = -1, nextSize = -1;
        for (int i = 0; i < productSizes.length; i++) {
            if (productSizes[i].toString().equals(product.getSize())) {
                if (i > 0) {
                    preSize = i - 1;
                }
                if (i < productSizes.length - 1) {
                    nextSize = i + 1;
                }
                break;
            }
        }

        if (!isProductCostAndPriceVallid(productSize, preSize, nextSize, productCost, productPrice)) {
            return;
        }

        if (productManageModel.getIngredientDetailBufferList().isEmpty()) {
            this.productPanel.showErrorMessage("Ingredient list must have at least one ingredient.");
            return;
        }

        product.setName(productNameInput);
        product.setCost(productCost);
        product.setPrice(productPrice);

        this.productManageModel.updateProduct(product);

        List<IngredientDetailModelInterface> ingredientDetailBufferList = this.productManageModel
                .getIngredientDetailBufferList();

        ingredientDetailBufferList.forEach(ingredientDetail -> {
            ingredientDetail.setProduct(product);
        });

        // Find deleted ingredient detail
        Iterator<IngredientDetailModelInterface> iterator = product.getAllIngredientDetail();

        List<IngredientDetailModelInterface> deletedIngredientDetailList = new ArrayList<>();

        while (iterator.hasNext()) {
            IngredientDetailModelInterface ingredientDetail = iterator.next();
            int id = ingredientDetailBufferList.indexOf(ingredientDetail);
            if (id == -1) {
                deletedIngredientDetailList.add(ingredientDetail);
            }
        }

        deletedIngredientDetailList.forEach(ingredientDetail -> {
            product.removeIngredientDetail(ingredientDetail);
        });

        // Find updated ingredient detail
        iterator = product.getAllIngredientDetail();

        for (int i = 0; i < ingredientDetailBufferList.size(); i++) {
            while (iterator.hasNext()) {
                IngredientDetailModelInterface ingredientDetail = iterator.next();
                if (ingredientDetailBufferList.get(i).equals(ingredientDetail)) {
                    product.updateIngredientDetail(ingredientDetail);
                    break;
                }
            }
        }

        // Find inserted ingredient detail
        iterator = product.getAllIngredientDetail();

        for (int i = 0; i < ingredientDetailBufferList.size(); i++) {
            boolean found = false;
            while (iterator.hasNext()) {
                IngredientDetailModelInterface ingredientDetail = iterator.next();
                if (ingredientDetailBufferList.get(i).equals(ingredientDetail)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                IngredientDetailModelInterface ingredientDetail = ingredientDetailBufferList.get(i);
                ingredientDetail.setProduct(product);
                product.addIngredientDetail(ingredientDetail);
            }
        }

        this.productPanel.exitEditState();
        productPanel.showInfoMessage("Update product data successfully.");
    }

    @Override
    public void requestRemoveProduct() {
        String productIDText = this.productPanel.getProductIDText();

        ProductModelInterface product = this.productManageModel.getProductByID(productIDText);

        Assert.assertNotNull(product);

        if (isProductCanDelete(product)) {
            productPanel.showErrorMessage("Can't delete product with existed bill including it.");
            return;
        }

        product.removeAllIngredientDetail();

        this.productManageModel.removeProduct(product);
        this.searchList.remove(product);

        productManageModel.getIngredientDetailBufferList().clear();

        productPanel.showInfoMessage("Delete product successfully.");
    }

    @Override
    public void requestProduceProduct() {
        int rowID = this.productPanel.getSelectedRow();
        if (rowID == -1) {
            productPanel.showErrorMessage("You should choose one product first.");
            return;
        }
        ProductModelInterface product = this.searchList.get(rowID);
        if (productProduceDialog == null) {
            productProduceDialog = new ProductProduceDialog(productPanel
                    .getMainFrame(), true, this);
        }

        productProduceDialog.setProuductIDText(product.getProductIDText());
        productProduceDialog.setProductName(product.getName());
        productProduceDialog.setTotalCost(String.valueOf(product.getCost()));
        productProduceDialog.setVisible(true);
    }

    @Override
    public void produceProduct() {
        int rowID = this.productPanel.getSelectedRow();
        Assert.assertNotEquals(rowID, -1);

        ProductModelInterface product = this.searchList.get(rowID);

        int produceAmount = productProduceDialog.getProduceAmountInput();

        Iterator<IngredientDetailModelInterface> iterator = product
                .getAllIngredientDetail();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_CHECK_PRODUCE_PRODUCT_CONDITION);

            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            product.setKeyArg(2, ProductModel.ID_HEADER, callableStatement);

            while (iterator.hasNext()) {
                IngredientDetailModelInterface ingredientDetail = iterator.next();

                callableStatement.setString(3, ingredientDetail.getIngredientName());
                callableStatement.setInt(4, produceAmount);

                callableStatement.execute();

                boolean ret = callableStatement.getBoolean(1);
                if (!ret) {
                    productProduceDialog.showErrorMessage("Ingredient name '"
                            + ingredientDetail.getIngredientName() + "' has not enough amount to produce.");
                    return;
                }
            }

            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        productManageModel.produceProduct(product, produceAmount);

        productPanel.showInfoMessage("Request to produce product successfully.");
    }

    @Override
    public void showTotalCostProductProduce() {
        int rowID = this.productPanel.getSelectedRow();
        Assert.assertNotEquals(rowID, -1);

        ProductModelInterface product = this.searchList.get(rowID);
        int produceAmount = productProduceDialog.getProduceAmountInput();
        long totalCost = product.getCost() * produceAmount;
        productProduceDialog.setTotalCost(String.valueOf(totalCost));
    }

    @Override
    public void requestImportExcel() {
        ExcelTransfer.importExcelFileToTable(productPanel.getTableProduct());
    }

    @Override
    public void requestCreateTemplateExcel() {
        ExcelTransfer.createExcelFileTemplate(productPanel.getTableProduct());
    }

    @Override
    public void requestExportExcel() {
        if (productPanel.getTableProductRowCount() == 0) {
            productPanel.showErrorMessage("Table product data is empty.");
        } else {
            ExcelTransfer.exportTableToExcel(productPanel.getTableProduct());
        }
    }

    @Override
    public void requestShowProductInfo() {
        if (productPanel.getEditState() == ProductPanel.EditState.ADD) {
            return;
        }

        int rowID = this.productPanel.getSelectedRow();
        if (rowID == -1) {
            return;
        }
        if (rowID >= searchList.size()) {
            throw new IndexOutOfBoundsException("Row index is not in bound.");
        }
        ProductModelInterface product = this.searchList.get(rowID);
        this.productManageModel.setIngredientDetailBufferList(product);
        this.productPanel.showProductInfo(product);
    }

    @Override
    public void requestEditIngredient() {
        if (dialogIngredientEditing == null) {
            dialogIngredientEditing = new IngredientEditDialog(productPanel.getMainFrame(), true, productManageModel, this);
        }
        dialogIngredientEditing.setVisible(true);
    }

    @Override
    public boolean isSearchMatching(String searchText, ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        boolean ret = searchText.isEmpty()
                || (FuzzySearch.weightedRatio(searchText, product.getName())
                >= AppConstant.SEARCH_SCORE_CUT_OFF);
        if (ret) {
            this.searchList.add(product);
        }
        return ret;
    }

    @Override
    public boolean deleteProductInSearchList(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException("Product instance is null.");
        }
        int id = this.searchList.indexOf(product);
        if (id >= 0) {
            this.searchList.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Iterator<ProductModelInterface> getAllProductData() {
        Iterator<ProductModelInterface> iterator = this.productManageModel.getAllProductData();
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public Iterator<ProductModelInterface> getProductBySearchName(String searchText) {
        Iterator<ProductModelInterface> iterator = this.productManageModel.getProductSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public void requestAddIngredientDetailBuffer() {
        String selectedIngredientName = dialogIngredientEditing.getSelectedIngredientName();

        if (selectedIngredientName == null) {
            dialogIngredientEditing.showErrorMessage("You should add new ingredient first");
            return;
        }

        String selectedUnitName = dialogIngredientEditing.getSelectedUnitName();

        if (selectedUnitName == null) {
            dialogIngredientEditing.showErrorMessage("Error: no unit is existed.");
            return;
        }

        float amountInput = dialogIngredientEditing.getAmountInput();

        if (amountInput == 0f) {
            dialogIngredientEditing.showErrorMessage("Amount value must be 1 at least.");
            return;
        }

        this.productManageModel.setBufferListModifiedFlag(true);

        List<IngredientDetailModelInterface> ingredientDetailBufferList
                = this.productManageModel.getIngredientDetailBufferList();

        boolean found = false;

        for (int i = 0; i < ingredientDetailBufferList.size(); i++) {
            IngredientDetailModelInterface ingredientDetail = ingredientDetailBufferList.get(i);
            if (ingredientDetail.getIngredientName().equals(selectedIngredientName)) {
                ingredientDetail.setUnitName(selectedUnitName);
                ingredientDetail.setAmount(amountInput);
                dialogIngredientEditing.updateRowTableIngredient(i, ingredientDetail);
                found = true;
                break;
            }
        }

        if (!found) {
            IngredientDetailModelInterface ingredientDetail = new IngredientDetailModel();
            ingredientDetail.setIngredientName(selectedIngredientName);
            ingredientDetail.setUnitName(selectedUnitName);
            ingredientDetail.setAmount(amountInput);
            ingredientDetailBufferList.add(ingredientDetail);
            dialogIngredientEditing.addRowTableIngredient(ingredientDetail);
        }
    }

    @Override
    public void requestRemoveIngredientDetailBuffer() {
        int tableIngredientRowCount = dialogIngredientEditing.getTableIngredientRowCount();

        if (tableIngredientRowCount == 0) {
            dialogIngredientEditing.showErrorMessage("Ingredient list is empty.");
            return;
        }

        int selectedRowTableIngredient = dialogIngredientEditing.getSelectedRowTableIngredient();

        if (selectedRowTableIngredient == -1) {
            dialogIngredientEditing.showErrorMessage("You should choose one ingredient in list.");
            return;
        }

        this.productManageModel.setBufferListModifiedFlag(true);

        dialogIngredientEditing.removeRowTableIngredient(selectedRowTableIngredient);

        List<IngredientDetailModelInterface> ingredientDetailBufferList
                = this.productManageModel.getIngredientDetailBufferList();

        ingredientDetailBufferList.remove(selectedRowTableIngredient);
    }

    @Override
    public void requestClearIngredientDetailBuffer() {
        int tableIngredientRowCount = dialogIngredientEditing.getTableIngredientRowCount();

        if (tableIngredientRowCount == 0) {
            dialogIngredientEditing.showErrorMessage("Ingredient list is empty.");
            return;
        }

        this.productManageModel.setBufferListModifiedFlag(true);

        List<IngredientDetailModelInterface> ingredientDetailBufferList
                = this.productManageModel.getIngredientDetailBufferList();

        ingredientDetailBufferList.clear();

        dialogIngredientEditing.clearTableIngredient();
    }

    @Override
    public void requestSaveIngredientDetailBuffer() {
        productPanel.showIngredientDetailList();

        dialogIngredientEditing.dispose();
    }

    @Override
    public void requestCancelEditIngredientDetail() {
        boolean modified = this.productManageModel.getBufferListModifiedFlag();

        if (modified) {
            int ret = JOptionPane.showConfirmDialog(dialogIngredientEditing, "Discard your change?",
                    IngredientEditDialog.DIALOG_TITLE, JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) {
                List<IngredientDetailModelInterface> ingredientDetailBufferList
                        = this.productManageModel.getIngredientDetailBufferList();

                ingredientDetailBufferList.clear();

                if (productPanel.getEditState() == ProductPanel.EditState.MODIFY) {
                    // Restore buffer
                    String productIDText = productPanel.getProductIDText();

                    ProductModelInterface product = this.productManageModel.getProductByID(productIDText);

                    this.productManageModel.setIngredientDetailBufferList(product);
                }

                dialogIngredientEditing.dispose();
            }
        } else {
            dialogIngredientEditing.dispose();
        }
    }

    @Override
    public void updateModifiedIngredientObserver(IngredientModelInterface ingredient) {
        if (dialogIngredientEditing != null) {
            dialogIngredientEditing.reloadIngredientInputData();
        }

        String productIDText = productPanel.getProductIDText();

        if (productIDText.isEmpty()) {
            return;
        }

        ProductModelInterface product = productManageModel.getProductByID(productIDText);

        product.reloadIngredientDetailList();

        this.productManageModel.setIngredientDetailBufferList(product);

        // reload view ingredient detail list
        productPanel.showIngredientDetailList();
    }

    @Override
    public boolean canCloseProductManagePanel() {
        if (productPanel.getEditState() == ProductPanel.EditState.ADD
                || productPanel.getEditState() == ProductPanel.EditState.MODIFY) {
            int ret = JOptionPane.showConfirmDialog(productPanel.getMainFrame(),
                    "Cancel editing product?",
                    "Cancel editing product confirm dialog",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) {
                productPanel.exitEditState();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateInsertedIngredientObserver(IngredientModelInterface ingredient) {
        if (dialogIngredientEditing != null) {
            dialogIngredientEditing.reloadIngredientInputData();
        }
    }

}
