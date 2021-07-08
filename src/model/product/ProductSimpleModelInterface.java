package model.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface ProductSimpleModelInterface {

    void setProductID(String id);

    void setName(String name);

    void setSize(ProductSize size);

    void setAmount(int amount);
    
    void setCost(long cost);
    
    void setPrice(long price);
    
    String getProductIDText();

    String getName();
    
    ProductSize getSize();
    
    int getAmount();
    
    long getCost();
    
    long getPrice();
    
    long getProfit();
    
    void setKeyArg(int index, String header, PreparedStatement preparedStatement);
    
    void setProperty(ResultSet resultSet);
}
