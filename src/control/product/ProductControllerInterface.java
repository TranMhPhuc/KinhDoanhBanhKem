package control.product;

import java.util.Iterator;
import model.product.ProductModelInterface;
import view.ingredient.InsertedIngredientObserver;
import view.ingredient.ModifiedIngredientObserver;
import view.product.ProductPanel;

public interface ProductControllerInterface extends ModifiedIngredientObserver,
        InsertedIngredientObserver {

    void setProductPanelView(ProductPanel productPanel);
    
    void requestCreateProduct();
    
    void requestUpdateProduct();
    
    void requestRemoveProduct();
    
    void requestProduceProduct();
    
    void requestExportExcel();
    
    void requestCreateTemplateExcel();
    
    void requestShowProductInfo();
    
    void requestEditIngredient();
    
    boolean isSearchMatching(String searchText, ProductModelInterface product);
    
    boolean deleteProductInSearchList(ProductModelInterface product);
    
    Iterator<ProductModelInterface> getAllProductData();
    
    Iterator<ProductModelInterface> getProductBySearchName(String searchText);
    
    void requestAddIngredientDetailBuffer();
    
    void requestRemoveIngredientDetailBuffer();
    
    void requestClearIngredientDetailBuffer();
    
    void requestSaveIngredientDetailBuffer();
    
    void requestCancelEditIngredientDetail();
    
    void produceProduct();
    
    void showTotalCostProductProduce();
    
    boolean canCloseProductManagePanel();
    
}
