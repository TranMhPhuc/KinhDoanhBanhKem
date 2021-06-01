package model.ingredient;

import java.time.LocalDate;

public class IngredientImportDetail {

    private IngredientModel ingredient;
    private LocalDate importDay;
    private int importAmount;
    private int importCost;
    private IngredientUnit unit;

    public IngredientImportDetail(IngredientModel ingredient, LocalDate importDay, int importAmount, int importCost, IngredientUnit unit) {
        this.ingredient = ingredient;
        this.importDay = importDay;
        this.importAmount = importAmount;
        this.importCost = importCost;
        this.unit = unit;
    }

    public void setIngredient(IngredientModel ingredient) {
        this.ingredient = ingredient;
    }

    public void setImportDay(LocalDate importDay) {
        this.importDay = importDay;
    }

    public void setImportAmount(int importAmount) {
        this.importAmount = importAmount;
    }

    public void setImportCost(int importCost) {
        this.importCost = importCost;
    }

    public void setUnit(IngredientUnit unit) {
        this.unit = unit;
    }

    public IngredientModel getIngredient() {
        return ingredient;
    }

    public LocalDate getImportDay() {
        return importDay;
    }

    public int getImportAmount() {
        return importAmount;
    }

    public int getImportCost() {
        return importCost;
    }

    public IngredientUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "IngredientImportDetail{" + "ingredient=" + ingredient + ", importDay=" + importDay + ", importAmount=" + importAmount + ", importCost=" + importCost + ", unit=" + unit + '}';
    }
}
