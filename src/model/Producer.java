package Model;

import java.util.Objects;

public class Producer {

    private String producerCode;
    private String producerName;
    private String email;
    private String address;
    private int phoneNum;

    public Producer() {

    }

    public Producer(String producerCode, String producerName, String email, String address, int phoneNum) {
        this.producerCode = producerCode;
        this.producerName = producerName;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public String getProducerName() {
        return producerName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.producerCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producer other = (Producer) obj;
        if (!Objects.equals(this.producerCode, other.producerCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producer{" + "producerCode=" + producerCode + ", producerName=" + producerName + ", email=" + email + ", address=" + address + ", phoneNum=" + phoneNum + '}';
    }

}//end Producer
