package control.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import model.product.ProductManageModelInterface;
import model.product.ProductModel;
import model.product.ProductModelInterface;
import org.junit.Assert;
import util.constant.AppConstant;
import view.function.product.ProductPanel;

public class ProductController implements ProductControllerInterface {

    private volatile static ProductController uniqueInstance;

    private List<ProductModelInterface> searchList;

    private ProductManageModelInterface model;
    private ProductPanel view;

    private ProductController(ProductManageModelInterface model) {
        this.searchList = new ArrayList<>();

        this.model = model;
        this.view = ProductPanel.getInstance(model, this);
    }

    public static final ProductControllerInterface getInstance(ProductManageModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (ProductController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProductController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static final ProductControllerInterface getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    @Override
    public void requestCreateProduct() {
        String productIDText = this.view.getProductIDText();

        String productNameInput = this.view.getProductName();

        if (productNameInput.isEmpty()) {
            this.view.showErrorMessage("Product name is required.");
            return;
        }
        
        String productSizeInput = this.view.getProductSize();

        // Check if same name but not size
        Iterator<ProductModelInterface> iterator = this.model.getAllProductData();
        while (iterator.hasNext()) {
            ProductModelInterface element = iterator.next();
            if (element.getName().equals(productNameInput)
                    && element.getSize().equals(productSizeInput)) {
                this.view.showErrorMessage("Product with same name can't have existed size value.");
                return;
            }
        }

        String productCostInput = this.view.getProductCost();

        if (productCostInput.isEmpty()) {
            this.view.showErrorMessage("Product cost is required.");
            return;
        }

        long productCost = 0;
        try {
            productCost = Long.parseLong(productCostInput);
        } catch (NumberFormatException ex) {
            this.view.showErrorMessage("Product cost is invallid.");
            return;
        }

        if (productCost <= 0) {
            this.view.showErrorMessage("Product cost is not negative.");
            return;
        }

        String productPriceInput = this.view.getProductPrice();

        if (productCostInput.isEmpty()) {
            this.view.showErrorMessage("Product price is required.");
            return;
        }

        long productPrice = 0;
        try {
            productPrice = Long.parseLong(productPriceInput);
        } catch (NumberFormatException ex) {
            this.view.showErrorMessage("Product price is invallid.");
            return;
        }

        if (productPrice <= 0) {
            this.view.showErrorMessage("Product cost is not negative.");
            return;
        }

        if (productPrice < 1.1 * productCost) {
            this.view.showErrorMessage("Product price must be greater at least 10% than cost.");
            return;
        }

        int productAmount = this.view.getProductAmount();
        
        ProductModelInterface product = new ProductModel();
        product.setName(productNameInput);
        product.setAmount(productAmount);
        product.setCost(productCost);
        product.setPrice(productPrice);
        
        this.model.addNewProduct(product);
        
        this.view.exitEditState();
        this.view.showInfoMessage("Inser new product successfully.");
    }

    @Override
    public void requestUpdateProduct() {
        String productIDText = this.view.getProductIDText();

        ProductModelInterface product = this.model.getProductByID(productIDText);

        Assert.assertNotNull(product);

        String productNameInput = this.view.getProductName();

        if (!product.getName().equals(productNameInput)) {
            if (productNameInput.isEmpty()) {
                this.view.showErrorMessage("Product name is required.");
                return;
            }
        }

        String productSizeInput = this.view.getProductSize();

        if (!product.getName().equals(productNameInput)
                || !product.getSize().equals(productSizeInput)) {
            // Check if same name but not size
            Iterator<ProductModelInterface> iterator = this.model.getAllProductData();
            while (iterator.hasNext()) {
                ProductModelInterface element = iterator.next();
                if (element.getName().equals(productNameInput)
                        && element.getSize().equals(productSizeInput)) {
                    this.view.showErrorMessage("Product with same name can't have existed size value.");
                    return;
                }
            }
        }

        String productCostInput = this.view.getProductCost();

        if (productCostInput.isEmpty()) {
            this.view.showErrorMessage("Product cost is required.");
            return;
        }

        long productCost = 0;
        try {
            productCost = Long.parseLong(productCostInput);
        } catch (NumberFormatException ex) {
            this.view.showErrorMessage("Product cost is invallid.");
            return;
        }

        if (productCost <= 0) {
            this.view.showErrorMessage("Product cost is not negative.");
            return;
        }

        String productPriceInput = this.view.getProductPrice();

        if (productCostInput.isEmpty()) {
            this.view.showErrorMessage("Product price is required.");
            return;
        }

        long productPrice = 0;
        try {
            productPrice = Long.parseLong(productPriceInput);
        } catch (NumberFormatException ex) {
            this.view.showErrorMessage("Product price is invallid.");
            return;
        }

        if (productPrice <= 0) {
            this.view.showErrorMessage("Product cost is not negative.");
            return;
        }

        if (productPrice < 1.1 * productCost) {
            this.view.showErrorMessage("Product price must be greater at least 10% than cost.");
            return;
        }

        int productAmount = this.view.getProductAmount();

        product.setName(productNameInput);
        product.setAmount(productAmount);
        product.setCost(productCost);
        product.setPrice(productPrice);

        this.model.updateProduct(product);

        this.view.exitEditState();
        this.view.showInfoMessage("Update product data successfully.");
    }

    @Override
    public void requestRemoveProduct() {
        String productIDText = this.view.getProductIDText();

        ProductModelInterface product = this.model.getProductByID(productIDText);

        Assert.assertNotNull(product);

        if (this.model.isProductOfAnyBill(product)) {
            this.view.showErrorMessage("Can't delete product with existed bill including it.");
            return;
        }

        this.model.removeProduct(product);
        this.searchList.remove(product);

        this.view.showInfoMessage("Delete product successfully.");
    }

    @Override
    public void requestProduceProduct() {
        // XXX

    }

    @Override
    public void requestImportExcel() {
        // XXX
    }

    @Override
    public void requestExportExcel() {
        // XXX

    }

    @Override
    public void requestShowProductInfo() {
        int rowID = this.view.getSelectedRow();
        if (rowID == -1) {
            return;
        }
        if (rowID >= searchList.size()) {
            throw new IndexOutOfBoundsException("Row index is not in bound.");
        }
        ProductModelInterface product = this.searchList.get(rowID);
        this.view.showProductInfo(product);
    }

    @Override
    public void requestEditIngredientOfProduct() {
        // XXX
    }

    @Override
    public boolean insertToSearchListByMatchingName(String searchText, ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        boolean ret = searchText.isEmpty()
                || (FuzzySearch.ratio(searchText, product.getName()) >= AppConstant.SEARCH_SCORE_CUT_OFF);
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
        Iterator<ProductModelInterface> iterator = this.model.getAllProductData();
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public Iterator<ProductModelInterface> getProductBySearchName(String searchText) {
        Iterator<ProductModelInterface> iterator = this.model.getProductSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

}
