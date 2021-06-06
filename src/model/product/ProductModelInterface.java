package model.product;

import model.DatabaseModel;

public interface ProductModelInterface extends DatabaseModel {

    void setString(String name);
    
    void setSize(String size);
    
    void setCost(int cost);
    
    void setAmount(int amount);
    
    void setPrice(int price);
    
    String getProductIDText();

    String getName();
    
    String getSize();
    
    int getCost();
    
    int getAmount();
    
    int getPrice();
    
    String getCostText();
    
    String getAmountText();
    
    String getPriceText();
}
