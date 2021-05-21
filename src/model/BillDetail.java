package model;

import java.util.HashMap;
import java.util.Map;
import org.javatuples.Pair;

public class BillDetail {

    private Bill bill;
    private HashMap<Cake, Pair<Integer, Integer>> products;

    public BillDetail() {
        bill = new Bill();
        products = new HashMap<>();
    }

    public Bill getBill() {
        return bill;
    }

    public HashMap<Cake, Pair<Integer, Integer>> getProducts() {
        return products;
    }

    public boolean havingProduct(Cake cake) {
        return products.containsKey(cake);
    }

    public void addProduct(Cake cake) {
        Pair<Integer, Integer> value = products.get(cake);

        if (value != null) {
            value.setAt0(value.getValue0() + 1);
            value.setAt1(value.getValue1() + cake.getBareCost());
        } else {
            products.put(cake, new Pair<>(0, 0));
        }

        bill.setTotalCost(bill.getTotalCost() + cake.getBareCost());
    }

    public void removeProduct(Cake cake) throws IllegalArgumentException {
        Pair<Integer, Integer> value = products.get(cake);

        if (value != null) {
            value.setAt0(value.getValue0() - 1);
            value.setAt1(value.getValue1() - cake.getBareCost());
        } else {
            throw new IllegalArgumentException("Product removed is not available.");
        }

        bill.setTotalCost(bill.getTotalCost() - cake.getBareCost());
    }

    Pair<Integer, Integer> getAmountAndCost(Cake cake) {
        return products.get(cake);
    }

    public String getAmountText(Cake cake) {
        return String.valueOf(products.get(cake).getValue0());
    }

    public String getText(Cake cake) {
        return String.valueOf(products.get(cake).getValue1());
    }

    public String getTotalCostText(Cake cake) {
        return String.valueOf(bill.getTotalCost());
    }
}
