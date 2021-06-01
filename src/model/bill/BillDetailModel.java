package model.bill;

import java.util.HashMap;
import model.product.ProductModel;
import org.javatuples.Pair;

public class BillDetailModel {

    private BillModel bill;
    private HashMap<ProductModel, Pair<Integer, Integer>> products;

    public BillDetailModel() {
        products = new HashMap<>();
    }

    public BillModel getBill() {
        return bill;
    }

    public HashMap<ProductModel, Pair<Integer, Integer>> getProducts() {
        return products;
    }

    public boolean havingProduct(ProductModel cake) {
        return products.containsKey(cake);
    }

    public void addProduct(ProductModel cake) {
        Pair<Integer, Integer> value = products.get(cake);

        if (value != null) {
            value.setAt0(value.getValue0() + 1);
            value.setAt1(value.getValue1() + cake.getBareCost());
        } else {
            products.put(cake, new Pair<>(0, 0));
        }

//        bill.setTotalCost(bill.getPayment()+ cake.getBareCost());
    }

    public void removeProduct(ProductModel cake) throws IllegalArgumentException {
        Pair<Integer, Integer> value = products.get(cake);

        if (value != null) {
            value.setAt0(value.getValue0() - 1);
            value.setAt1(value.getValue1() - cake.getBareCost());
        } else {
            throw new IllegalArgumentException("Product removed is not available.");
        }

//        bill.setTotalCost(bill.getTotalCost() - cake.getBareCost());
    }

    Pair<Integer, Integer> getAmountAndCost(ProductModel cake) {
        return products.get(cake);
    }

    public String getAmountText(ProductModel cake) {
        return String.valueOf(products.get(cake).getValue0());
    }

    public String getText(ProductModel cake) {
        return String.valueOf(products.get(cake).getValue1());
    }

//    public String getTotalCostText(ProductModel cake) {
//        return String.valueOf(bill.getTotalCost());
//    }
}
