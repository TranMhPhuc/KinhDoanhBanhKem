package model;

import java.util.Objects;

public class IngredientUnit {

    private static IngredientUnit gamUnit = new IngredientUnit();
    private static IngredientUnit mlUnit = new IngredientUnit();
    private static IngredientUnit quaUnit = new IngredientUnit();
    
    private int unitCode;
    private String unitName;

    private IngredientUnit() {
    }

    private IngredientUnit(int unitCode, String unitName) {
        this.unitCode = unitCode;
        this.unitName = unitName;
    }

}//end IngredientUnit
