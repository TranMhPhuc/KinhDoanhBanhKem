package model.bill.detail;

import model.DatabaseModel;
import model.product.ProductModelInterface;
import model.bill.BillModelInterface;

public interface ProductDetailModelInterface extends DatabaseModel {

    void setBill(BillModelInterface bill);

    void setProduct(ProductModelInterface product);

    void setAmount(int amount);

    void setPrice(long price);

    BillModelInterface getBill();

    ProductModelInterface getProduct();

    int getAmount();

    long getPrice();

}
