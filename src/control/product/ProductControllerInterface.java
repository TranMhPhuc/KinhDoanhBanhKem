package control.product;

import java.util.Iterator;
import model.product.ProductModelInterface;

public interface ProductControllerInterface {

    void requestCreateProduct();
    
    void requestUpdateProduct();
    
    void requestRemoveProduct();
    
    void requestProduceProduct();
    
    void requestImportExcel();
    
    void requestExportExcel();
    
    void requestShowProductInfo();
    
    void requestEditIngredientOfProduct();
    
    boolean insertToSearchListByMatchingName(String searchText, ProductModelInterface product);
    
    boolean deleteProductInSearchList(ProductModelInterface product);
    
    Iterator<ProductModelInterface> getAllProductData();
    
    Iterator<ProductModelInterface> getProductBySearchName(String searchText);
    
}
