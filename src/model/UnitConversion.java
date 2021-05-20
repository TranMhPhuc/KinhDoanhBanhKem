package model;

public class UnitConversion {

    private IngredientUnit unitFrom;
    private IngredientUnit unitTo;
    private float convertFactor;

    public UnitConversion() {
    }

    public UnitConversion(IngredientUnit unitFrom, IngredientUnit unitTo, float convertFactor) {
        this.unitFrom = unitFrom;
        this.unitTo = unitTo;
        this.convertFactor = convertFactor;
    }

}
