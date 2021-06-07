package model.provider;

import model.DatabaseModel;

public interface ProviderModelInterface extends DatabaseModel {

    void setProviderID(String id);

    void setName(String name);

    void setEmail(String email);

    void setAddress(String address);

    void setPhoneNum(String phoneNum);

    String getProviderIDText();

    String getName();

    String getEmail();

    String getAddress();

    String getPhoneNum();

}
