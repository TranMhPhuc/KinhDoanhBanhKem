package model;

import java.util.Objects;

public class Ingredient {

    private String ingreCode;
    private String ingreName;
    private String ingreType;
    private int ingreCost;
    private int totalAmount;
    private String producerCode;

    public Ingredient() {

    }

    public Ingredient(String ingreCode, String ingreName, String ingreType, int ingreCost, int totalAmount, String producerCode) {
        this.ingreCode = ingreCode;
        this.ingreName = ingreName;
        this.ingreType = ingreType;
        this.ingreCost = ingreCost;
        this.totalAmount = totalAmount;
        this.producerCode = producerCode;
    }

    public String getIngreCode() {
        return ingreCode;
    }

    public String getIngreName() {
        return ingreName;
    }

    public String getIngreType() {
        return ingreType;
    }

    public int getIngreCost() {
        return ingreCost;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public void setIngreCode(String ingreCode) {
        this.ingreCode = ingreCode;
    }

    public void setIngreName(String ingreName) {
        this.ingreName = ingreName;
    }

    public void setIngreType(String ingreType) {
        this.ingreType = ingreType;
    }

    public void setIngreCost(int ingreCost) {
        this.ingreCost = ingreCost;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.ingreCode);
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
        final Ingredient other = (Ingredient) obj;
        if (!Objects.equals(this.ingreCode, other.ingreCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "ingreCode=" + ingreCode + ", ingreName=" + ingreName + ", ingreType=" + ingreType + ", ingreCost=" + ingreCost + ", totalAmount=" + totalAmount + ", producerCode=" + producerCode + '}';
    }

}//end Ingredient
