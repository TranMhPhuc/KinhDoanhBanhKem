package model;

import java.util.Objects;

public class IngredientType {

    private String ingreTypeCode;
    private String ingreTypeName;
    private String computeUnit;

    public IngredientType() {

    }

    public String getIngreTypeCode() {
        return ingreTypeCode;
    }

    public String getIngreTypeName() {
        return ingreTypeName;
    }

    public String getComputeUnit() {
        return computeUnit;
    }

    public void setIngreTypeCode(String ingreTypeCode) {
        this.ingreTypeCode = ingreTypeCode;
    }

    public void setIngreTypeName(String ingreTypeName) {
        this.ingreTypeName = ingreTypeName;
    }

    public void setComputeUnit(String computeUnit) {
        this.computeUnit = computeUnit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.ingreTypeCode);
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
        final IngredientType other = (IngredientType) obj;
        if (!Objects.equals(this.ingreTypeCode, other.ingreTypeCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IngredientType{" + "ingreTypeCode=" + ingreTypeCode + ", ingreTypeName=" + ingreTypeName + ", computeUnit=" + computeUnit + '}';
    }

}//end IngredientType
