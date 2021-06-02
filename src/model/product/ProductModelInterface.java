package model.product;

import model.ingredientOfProduct.IngredientOfProductDetailInterface;
import model.productOfBill.ProductOfBillDetailInterface;

public interface ProductModelInterface {
    String getProductIDText();
    
    void addIngredientDetail(IngredientOfProductDetailInterface ingredientOfProductDetailInterface);
    
    void addBillDetail(ProductOfBillDetailInterface productOfBillDetailInterface);
    
    void registerObserver();
    
    void removeObserver();
    
    void notifyObserver();
}
