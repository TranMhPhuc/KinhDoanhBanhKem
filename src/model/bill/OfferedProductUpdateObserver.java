package model.bill;

import model.product.ProductSimpleModelInterface;

public interface OfferedProductUpdateObserver {

    void updateOfferedProductInfo(ProductSimpleModelInterface product);
    
}
