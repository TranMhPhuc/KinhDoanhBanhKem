package model.bill.detail;

import model.DatabaseModel;
import model.bill.BillModelInterface;
import model.product.ProductSimpleModelInterface;

public interface ProductDetailModelInterface extends DatabaseModel {

    void setBill(BillModelInterface bill);

    void setProduct(ProductSimpleModelInterface product);

    void setAmount(int amount);

    BillModelInterface getBill();

    ProductSimpleModelInterface getProduct();

    int getAmount();

    long getPrice();

}
