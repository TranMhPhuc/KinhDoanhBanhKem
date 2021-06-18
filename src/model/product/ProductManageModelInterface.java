package model.product;

import java.util.Iterator;
import java.util.List;
import model.DatabaseUpdate;
import model.product.ingredientDetail.IngredientDetailModelInterface;
import view.product.InsertedProductObserver;
import view.product.ModifiedProductObserver;
import view.product.RemovedProductObserver;

public interface ProductManageModelInterface extends DatabaseUpdate {

    void registerInsertedProductObserver(InsertedProductObserver observer);

    void removeInsertedProductObserver(InsertedProductObserver observer);

    void registerModifiedProductObserver(ModifiedProductObserver observer);

    void removeModifiedProductObserver(ModifiedProductObserver observer);

    void registerRemovedProductObserver(RemovedProductObserver observer);

    void removeRemovedProductObserver(RemovedProductObserver observer);
    
    //=========================================================================
    
    String getNextProductIDText();
    
    void exportProductData();
    
    void importProductData();
    
    //=========================================================================
    
    void addProduct(ProductModelInterface newProduct);
    
    boolean updateProduct(ProductModelInterface updatedProduct);
    
    boolean removeProduct(ProductModelInterface removedProduct);
    
    //=========================================================================
    
    Iterator<ProductModelInterface> getAllProductData();
    
    ProductModelInterface getProductByID(String productIDText);
    
    Iterator<ProductModelInterface> getProductSearchByName(String searchText);
    
    Iterator<ProductModelInterface> getProductByName(String productName);
    
    ProductModelInterface getProductByNameAndSize(String productName, String productSize);
    
    List<String> getAllIngredientName();
    
    List<String> getUnitNamePossibleOfIngredient(String ingredientName);
    
    void setIngredientDetailBufferList(ProductModelInterface product);
    
    List<IngredientDetailModelInterface> getIngredientDetailBufferList();
    
    void setBufferListModifiedFlag(boolean modified);
    
    boolean getBufferListModifiedFlag();
    
    void produceProduct(ProductModelInterface product, int produceAmount);
    
}
