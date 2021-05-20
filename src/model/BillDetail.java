package model;

import java.util.HashMap;
import java.util.Map;

public class BillDetail {

    private Bill bill;
    private HashMap<Cake, Integer> products;
    private int totalCost = 0;

    public BillDetail(Bill bill) {
        this.bill = bill;
        products = new HashMap<Cake, Integer>();
    }

    public void addProduct(Cake cake) {
        Integer value = products.get(cake);

        if (value != null) {
            products.put(cake, value++);
        } else {
            products.put(cake, 0);
        }

        totalCost += cake.getBareCost();
    }

    public void removeProduct(Cake cake) throws Exception {
        Integer value = products.get(cake);

        if (value != null) {
            products.put(cake, value--);
        } else {
            throw new Exception("Removed cake is not available.");
        }

        totalCost -= cake.getBareCost();
        checkTotalCost();
    }

    public int getTotalCost() {
        return this.totalCost;
    }

    private void checkTotalCost() {
        if (this.totalCost < 0) {
            throw new AssertionError("Total cost is negative!");
        }
    }
}
