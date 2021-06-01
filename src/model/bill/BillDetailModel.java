package model.bill;

import java.util.HashMap;
import model.product.Product;
import org.javatuples.Pair;

public class BillDetailModel {

    private BillModel bill;
    private HashMap<Product, Pair<Integer, Integer>> products;

    public BillDetailModel() {
        bill = new BillModel();
        products = new HashMap<>();
    }

    public BillModel getBill() {
        return bill;
    }

    public HashMap<Product, Pair<Integer, Integer>> getProducts() {
        return products;
    }

    public boolean havingProduct(Product cake) {
        return products.containsKey(cake);
    }

    public void addProduct(Product cake) {
        Pair<Integer, Integer> value = products.get(cake);

        if (value != null) {
            value.setAt0(value.getValue0() + 1);
            value.setAt1(value.getValue1() + cake.getBareCost());
        } else {
            products.put(cake, new Pair<>(0, 0));
        }

        bill.setTotalCost(bill.getTotalCost() + cake.getBareCost());
    }

    public void removeProduct(Product cake) throws IllegalArgumentException {
        Pair<Integer, Integer> value = products.get(cake);

        if (value != null) {
            value.setAt0(value.getValue0() - 1);
            value.setAt1(value.getValue1() - cake.getBareCost());
        } else {
            throw new IllegalArgumentException("Product removed is not available.");
        }

        bill.setTotalCost(bill.getTotalCost() - cake.getBareCost());
    }

    Pair<Integer, Integer> getAmountAndCost(Product cake) {
        return products.get(cake);
    }

    public String getAmountText(Product cake) {
        return String.valueOf(products.get(cake).getValue0());
    }

    public String getText(Product cake) {
        return String.valueOf(products.get(cake).getValue1());
    }

    public String getTotalCostText(Product cake) {
        return String.valueOf(bill.getTotalCost());
    }
}
