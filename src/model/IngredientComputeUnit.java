package Model;

import java.util.Objects;

public class IngredientComputeUnit {

    private String unitCode;
    private String unitName;
    private boolean canChange = true;

    public IngredientComputeUnit() {

    }

    public IngredientComputeUnit(String unitCode, String unitName) {
        this.unitCode = unitCode;
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public boolean isCanChange() {
        return canChange;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setCanChange(boolean canChange) {
        this.canChange = canChange;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.unitCode);
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
        final IngredientComputeUnit other = (IngredientComputeUnit) obj;
        if (!Objects.equals(this.unitCode, other.unitCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IngredientComputeUnit{" + "unitCode=" + unitCode + ", unitName=" + unitName + ", canChange=" + canChange + '}';
    }

}//end IngredientComputeUnit
