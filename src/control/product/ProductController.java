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
import model.product.ProductSimpleModel;
import model.product.ProductSize;
import model.product.ingredientDetail.IngredientDetailModel;
import model.product.ingredientDetail.IngredientDetailModelInterface;
import model.setting.AppSetting;
import org.junit.Assert;
import util.common.string.StringUtil;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import util.excel.ExcelTransfer;
import util.messages.Messages;
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
    private ProductProduceDialog dialogProductProduce;

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
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_NAME_EMPTY);
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
                productPanel.showErrorMessage(Messages.getInstance().PRODUCT_EXISTS);
                return false;
            }
        }

        return true;
    }

    private boolean isProductCostInputVallid(long productCost) {
        if (productCost <= 0) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_COST_LESS_THAN_1);
            return false;
        }
        return true;
    }

    private boolean isProductPriceInputVallid(long productPrice, long productCost) {
        if (productPrice <= 0) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_LESS_THAN_1);
            return false;
        }

        if (productPrice < 1.1 * productCost) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_10_COST);
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
                    productPanel.showErrorMessage(Messages.getInstance().PRODUCT_COST_LESS_SMALLER_SIZE
                            + productSizes[preSize].name() + " (" + preSizeProduct.getCost()
                            + " > " + productCost + ").");
                    return false;
                }

                if (preSizeProduct.getPrice() > productPrice) {
                    productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_LESS_SMALLER_SIZE
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
                    productPanel.showErrorMessage(Messages.getInstance().PRODUCT_COST_GREATER_BIGGER_SIZE
                            + productSizes[nextSize].name() + " (" + nextSizeProduct.getCost()
                            + " < " + productCost + ").");
                    return false;
                }

                if (nextSizeProduct.getPrice() < productPrice) {
                    productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_GREATER_BIGGER_SIZE
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
            product.setKeyArg(2, ProductSimpleModel.ID_HEADER, callableStatement);

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
        productNameInput = StringUtil.getCapitalizeWord(productNameInput);

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
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_COST_EMPTY);
            return;
        }

        long productCost = 0;
        try {
            productCost = Long.parseLong(productCostInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_COST_INVALID);
            return;
        }

        if (!isProductCostInputVallid(productCost)) {
            return;
        }

        // Check product price
        String productPriceInput = this.productPanel.getProductPrice();

        if (productPriceInput.isEmpty()) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_EMPTY);
            return;
        }

        long productPrice = 0;
        try {
            productPrice = Long.parseLong(productPriceInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_INVALID);
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

        if (productManageModel.getBufferedIngredientDetailList().isEmpty()) {
            this.productPanel.showErrorMessage(Messages.getInstance().PRODUCT_SAVE_INGR_LIST_EMPTY);
            return;
        }

        ProductModelInterface product = new ProductModel();
        product.setProductID(productIDText);
        product.setName(productNameInput);
        product.setSize(ProductSize.getProductSizeFromString(productSize));
        product.setCost(productCost);
        product.setPrice(productPrice);

        this.productManageModel.addProduct(product);

        productManageModel.getBufferedIngredientDetailList().forEach(ingredientDetail -> {
            ingredientDetail.setProduct(product);
            product.addIngredientDetail(ingredientDetail);
        });

        this.productPanel.exitEditState();
        productPanel.showInfoMessage(Messages.getInstance().PRODUCT_INSERTED_SUCCESSFULLY);
    }

    @Override
    public void requestUpdateProduct() {
        String productIDText = this.productPanel.getProductIDText();

        ProductModelInterface product = this.productManageModel.getProductByID(productIDText);

        Assert.assertNotNull(product);

        String productNameInput = this.productPanel.getProductName();
        productNameInput = StringUtil.getCapitalizeWord(productNameInput);

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
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_COST_EMPTY);
            return;
        }

        long productCost = 0;
        try {
            productCost = Long.parseLong(productCostInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_COST_INVALID);
            return;
        }

        if (!isProductCostInputVallid(productCost)) {
            return;
        }

        String productPriceInput = this.productPanel.getProductPrice();

        if (productPriceInput.isEmpty()) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_EMPTY);
            return;
        }

        long productPrice = 0;
        try {
            productPrice = Long.parseLong(productPriceInput);
        } catch (NumberFormatException ex) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_PRICE_INVALID);
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

        if (productManageModel.getBufferedIngredientDetailList().isEmpty()) {
            this.productPanel.showErrorMessage(Messages.getInstance().PRODUCT_SAVE_INGR_LIST_EMPTY);
            return;
        }

        product.setName(productNameInput);
        product.setCost(productCost);
        product.setPrice(productPrice);
        product.setSize(ProductSize.getProductSizeFromString(productSize));

        this.productManageModel.updateProduct(product);

        List<IngredientDetailModelInterface> bufferedIngredientDetailList = this.productManageModel
                .getBufferedIngredientDetailList();

        bufferedIngredientDetailList.forEach(ingredientDetail -> {
            ingredientDetail.setProduct(product);
        });

        // Find deleted ingredient detail
        Iterator<IngredientDetailModelInterface> iterator = product.getAllIngredientDetail();

        List<IngredientDetailModelInterface> tempList = new ArrayList<>();

        while (iterator.hasNext()) {
            IngredientDetailModelInterface ingredientDetail = iterator.next();
            int id = bufferedIngredientDetailList.indexOf(ingredientDetail);
            if (id == -1) {
                tempList.add(ingredientDetail);
            }
        }

        tempList.forEach(ingredientDetail -> {
            product.removeIngredientDetail(ingredientDetail);
        });

        tempList.clear();
        
        // Find updated and inserted ingredient detail
        for (int i = 0; i < bufferedIngredientDetailList.size(); i++) {
            IngredientDetailModelInterface ingredientDetail = bufferedIngredientDetailList.get(i);
            boolean found = false;
            iterator = product.getAllIngredientDetail();
            while (iterator.hasNext()) {
                if (ingredientDetail.equals(iterator.next())) {
                    product.updateIngredientDetail(ingredientDetail);
                    found = true;
                    break;
                }
            }

            if (!found) {
                tempList.add(ingredientDetail);
            }
        }

        tempList.forEach(ingredientDetail -> {
            ingredientDetail.setProduct(product);
            product.addIngredientDetail(ingredientDetail);
        });

        // Find inserted ingredient detail
//        for (int i = 0; i < bufferedIngredientDetailList.size(); i++) {
//            boolean found = false;
//            iterator = product.getAllIngredientDetail();
//            while (iterator.hasNext()) {
//                IngredientDetailModelInterface ingredientDetail = iterator.next();
//                if (bufferedIngredientDetailList.get(i).equals(ingredientDetail)) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                IngredientDetailModelInterface ingredientDetail = bufferedIngredientDetailList.get(i);
//                ingredientDetail.setProduct(product);
//                product.addIngredientDetail(ingredientDetail);
//            }
//        }
        this.productPanel.exitEditState();
        productPanel.showInfoMessage(Messages.getInstance().PRODUCT_UPDATED_SUCCESSFULLY);
    }

    @Override
    public void requestRemoveProduct() {
        String productIDText = this.productPanel.getProductIDText();

        ProductModelInterface product = this.productManageModel.getProductByID(productIDText);

        Assert.assertNotNull(product);

        if (isProductCanDelete(product)) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_CANT_REMOVE);
            return;
        }

        product.removeAllIngredientDetail();

        this.productManageModel.removeProduct(product);
        this.searchList.remove(product);

        productManageModel.getBufferedIngredientDetailList().clear();

        productPanel.showInfoMessage(Messages.getInstance().PRODUCT_REMOVED_SUCCESSFULLY);
    }

    @Override
    public void requestProduceProduct() {
        int rowID = this.productPanel.getSelectedRow();
        if (rowID == -1) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_NO_PRODUCT_CHOSEN);
            return;
        }
        ProductModelInterface product = this.searchList.get(rowID);
        if (dialogProductProduce == null) {
            dialogProductProduce = new ProductProduceDialog(productPanel
                    .getMainFrame(), true, this);
            AppSetting.getInstance().registerObserver(dialogProductProduce);
        }

        dialogProductProduce.setProuductIDText(product.getProductIDText());
        dialogProductProduce.setProductName(product.getName());
        dialogProductProduce.setTotalCost(String.valueOf(product.getCost()));
        dialogProductProduce.setVisible(true);
    }

    @Override
    public void produceProduct() {
        int rowID = this.productPanel.getSelectedRow();
        Assert.assertNotEquals(rowID, -1);

        ProductModelInterface product = this.searchList.get(rowID);

        int produceAmount = dialogProductProduce.getProduceAmountInput();

        Iterator<IngredientDetailModelInterface> iterator = product
                .getAllIngredientDetail();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_CHECK_PRODUCE_PRODUCT_CONDITION);

            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            product.setKeyArg(2, ProductSimpleModel.ID_HEADER, callableStatement);

            while (iterator.hasNext()) {
                IngredientDetailModelInterface ingredientDetail = iterator.next();

                callableStatement.setString(3, ingredientDetail.getIngredientName());
                callableStatement.setInt(4, produceAmount);

                callableStatement.execute();

                boolean ret = callableStatement.getBoolean(1);
                if (!ret) {
                    dialogProductProduce.showErrorMessage(Messages.getInstance().PRODUCT_NOT_ENOUGH_INGR_1
                            + ingredientDetail.getIngredientName() + Messages.getInstance().PRODUCT_NOT_ENOUGH_INGR_2);
                    return;
                }
            }

            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        productManageModel.produceProduct(product, produceAmount);

        productPanel.showInfoMessage(Messages.getInstance().PRODUCT_PRODUCED_SUCCESSFULLY);
    }

    @Override
    public void showTotalCostProductProduce() {
        int rowID = this.productPanel.getSelectedRow();
        Assert.assertNotEquals(rowID, -1);

        ProductModelInterface product = this.searchList.get(rowID);
        int produceAmount = dialogProductProduce.getProduceAmountInput();
        long totalCost = product.getCost() * produceAmount;
        dialogProductProduce.setTotalCost(String.valueOf(totalCost));
    }

    @Override
    public void requestCreateTemplateExcel() {
        ExcelTransfer.createExcelFileTemplate(productPanel.getTableProduct());
    }

    @Override
    public void requestExportExcel() {
        if (productPanel.getTableProductRowCount() == 0) {
            productPanel.showErrorMessage(Messages.getInstance().PRODUCT_TABLE_EMPTY);
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
            AppSetting.getInstance().registerObserver(dialogIngredientEditing);
        }
        int rowID = productPanel.getSelectedRow();

        String name = "";
        String size = "";
        if (rowID != -1) {
            ProductModelInterface product = this.searchList.get(rowID);
            name = product.getName();
            size = product.getSize().name();
        }

        boolean isNewProduct;
        if (productPanel.getEditState() == ProductPanel.EditState.MODIFY) {
            isNewProduct = false;
        } else {
            isNewProduct = true;
        }
        dialogIngredientEditing.setNameAndSizeText(name, size, isNewProduct);
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
            dialogIngredientEditing.showErrorMessage(Messages.getInstance().PRODUCT_NO_INGR_CHOSEN);//??
            return;
        }

        String selectedUnitName = dialogIngredientEditing.getSelectedUnitName();

        if (selectedUnitName == null) {
            dialogIngredientEditing.showErrorMessage(Messages.getInstance().PRODUCT_NO_UNIT_CHOSEN);
            return;
        }

        float amountInput = dialogIngredientEditing.getAmountInput();

        if (amountInput == 0f) {
            dialogIngredientEditing.showErrorMessage(Messages.getInstance().PRODUCT_AMOUNT_AL_1);
            return;
        }

        this.productManageModel.setBufferListModifiedFlag(true);

        List<IngredientDetailModelInterface> ingredientDetailBufferList
                = this.productManageModel.getBufferedIngredientDetailList();

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
            dialogIngredientEditing.showErrorMessage(Messages.getInstance().PRODUCT_INGR_LIST_EMPTY);
            return;
        }

        int selectedRowTableIngredient = dialogIngredientEditing.getSelectedRowTableIngredient();

        if (selectedRowTableIngredient == -1) {
            dialogIngredientEditing.showErrorMessage(Messages.getInstance().PRODUCT_NO_INGR_CHOSEN);
            return;
        }

        this.productManageModel.setBufferListModifiedFlag(true);

        dialogIngredientEditing.removeRowTableIngredient(selectedRowTableIngredient);

        List<IngredientDetailModelInterface> ingredientDetailBufferList
                = this.productManageModel.getBufferedIngredientDetailList();

        ingredientDetailBufferList.remove(selectedRowTableIngredient);
    }

    @Override
    public void requestClearIngredientDetailBuffer() {
        int tableIngredientRowCount = dialogIngredientEditing.getTableIngredientRowCount();

        if (tableIngredientRowCount == 0) {
            dialogIngredientEditing.showErrorMessage(Messages.getInstance().PRODUCT_INGR_LIST_EMPTY);
            return;
        }

        this.productManageModel.setBufferListModifiedFlag(true);

        List<IngredientDetailModelInterface> ingredientDetailBufferList
                = this.productManageModel.getBufferedIngredientDetailList();

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
            int ret = JOptionPane.showConfirmDialog(dialogIngredientEditing, Messages.getInstance().PRODUCT_DISCARD_CHANGE,
                    "BakeryMS", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_WARNING);
            if (ret == JOptionPane.YES_OPTION) {
                List<IngredientDetailModelInterface> ingredientDetailBufferList
                        = this.productManageModel.getBufferedIngredientDetailList();

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

        Iterator<ProductModelInterface> products = productManageModel.getAllProductData();
        products.forEachRemaining(product -> {
            product.reloadIngredientDetailList();
        });

        String productIDText = productPanel.getProductIDText();

        if (productIDText.isEmpty()) {
            return;
        }

        ProductModelInterface product = productManageModel.getProductByID(productIDText);

        this.productManageModel.setIngredientDetailBufferList(product);

        // reload view ingredient detail list
        productPanel.showIngredientDetailList();
    }

    @Override
    public boolean canCloseProductManagePanel() {
        if (productPanel.getEditState() == ProductPanel.EditState.ADD
                || productPanel.getEditState() == ProductPanel.EditState.MODIFY) {
            int ret = JOptionPane.showConfirmDialog(productPanel.getMainFrame(),
                    Messages.getInstance().PRODUCT_CANEL_EDITING,
                    "BakeryMS",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_WARNING);
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
