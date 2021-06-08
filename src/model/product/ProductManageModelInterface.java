package model.product;

import java.util.Iterator;
import view.function.product.InsertedProductObserver;
import view.function.product.ModifiedProductObserver;
import view.function.product.RemovedProductObserver;
import model.ingredientOfProduct.IngredientDetailOfProductInterface;

public interface ProductManageModelInterface {

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
    
    void addNewProduct(ProductModelInterface newProduct);
    
    void updateProduct(ProductModelInterface updatedProduct);
    
    void removeProduct(ProductModelInterface removedProduct);
    
    //=========================================================================
    
    Iterator<ProductModelInterface> getAllProductData();
    
    ProductModelInterface getProductByID(String productIDText);
    
    Iterator<ProductModelInterface> getProductSearchByName(String searchText);
    
    boolean isProductOfAnyBill(ProductModelInterface product);
    
}
