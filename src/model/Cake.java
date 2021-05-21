package model;

import java.util.Objects;

public class Cake {

    private int cakeCode;
    private String cakeName;
    private int bareCost;
    private int amount = 0;
    private CakeSize cakeSize = CakeSize.M;

    public Cake() {

    }

    public Cake(int cakeCode, String cakeName, int bareCost) {
        this.cakeCode = cakeCode;
        this.cakeName = cakeName;
        this.bareCost = bareCost;
    }

    public int getCakeCode() {
        return cakeCode;
    }

    public String getCakeName() {
        return cakeName;
    }

    public int getBareCost() {
        return bareCost;
    }

    public int getAmount() {
        return amount;
    }

    public CakeSize getCakeSize() {
        return cakeSize;
    }

    public void setCakeCode(int cakeCode) {
        this.cakeCode = cakeCode;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public void setBareCost(int bareCost) {
        this.bareCost = bareCost;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCakeSize(CakeSize cakeSize) {
        this.cakeSize = cakeSize;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cakeCode);
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
        final Cake other = (Cake) obj;
        if (!Objects.equals(this.cakeCode, other.cakeCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cake{" + "cakeCode=" + cakeCode + ", cakeName=" + cakeName + ", bareCost=" + bareCost + ", amount=" + amount + ", cakeSize=" + cakeSize + '}';
    }
}//end Cake
