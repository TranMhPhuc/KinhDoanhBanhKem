package model.bill;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import model.bill.detail.ProductDetailModel;
import util.db.SQLServerConnection;
import view.bill.BillUpdateObserver;
import model.bill.detail.ProductDetailModelInterface;
import model.ingredient.IngredientManageModel;
import model.product.ProductSimpleModel;
import model.product.ProductSimpleModelInterface;
import model.user.UserModel;
import org.apache.commons.lang3.tuple.Pair;
import util.constant.AppConstant;

public class BillCreateModel implements BillCreateModelInterface {

    private static final String SP_GET_NEXT_IDENTITY_BILL
            = "{call get_next_identity_id_bill}";

    private static final String SP_GET_ALL_PRODUCT_DATA
            = "{call get_all_products}";

    private static Connection dbConnection;

    private List<ProductSimpleModelInterface> offeredProducts;
    private List<ProductDetailModelInterface> selectedProducts;
    private String nextBillIDText;
    private long totalMoneyOfBill;
    private long guestMoney;

    private List<BillUpdateObserver> billUpdateObservers;
    private List<OfferedProductUpdateObserver> offeredProductUpdateObservers;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    public BillCreateModel() {
        offeredProducts = new ArrayList<>();
        selectedProducts = new ArrayList<>();
        billUpdateObservers = new ArrayList<>();
        offeredProductUpdateObservers = new ArrayList<>();
        updateFromDB();
        updateNextBillID();
    }

    private void updateNextBillID() {
        int nextIdentity = 0;
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_NEXT_IDENTITY_BILL);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        nextBillIDText = String.valueOf(nextIdentity);
    }

    @Override
    public void setGuestMoney(long guestMoney) {
        this.guestMoney = guestMoney;
        billUpdateObservers.forEach(observer -> observer.updateFromBillState());
    }

    @Override
    public void updateFromDB() {
        offeredProducts.clear();
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_ALL_PRODUCT_DATA);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ProductSimpleModelInterface product = new ProductSimpleModel();
                product.setProperty(resultSet);
                offeredProducts.add(product);
            }

            resultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(BillCreateModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void clearData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNextBillIDText() {
        return this.nextBillIDText;
    }

    @Override
    public void registerBillUpdateObserver(BillUpdateObserver observer) {
        billUpdateObservers.add(observer);
    }

    @Override
    public void removeBillUpdateObserver(BillUpdateObserver observer) {
        int id = billUpdateObservers.indexOf(observer);
        if (id >= 0) {
            billUpdateObservers.remove(observer);
        }
    }

    @Override
    public void registerOfferedProductUpdateObserver(OfferedProductUpdateObserver observer) {
        offeredProductUpdateObservers.add(observer);
    }

    @Override
    public void removeOfferedProductUpdateObserver(OfferedProductUpdateObserver observer) {
        int id = offeredProductUpdateObservers.indexOf(observer);
        if (id >= 0) {
            offeredProductUpdateObservers.remove(id);
        }
    }

    @Override
    public void createBill() {
        BillModelInterface bill = new BillModel();

        bill.setBillIDText(nextBillIDText);
        bill.setGuestMoney(guestMoney);
        bill.setPayment(totalMoneyOfBill);
        bill.setEmployee(UserModel.getInstance().getImpl());
        bill.setDateTimeExport(Timestamp.from(Instant.now()));

        bill.insertToDatabase();

        for (ProductDetailModelInterface productDetail : selectedProducts) {
            productDetail.setBill(bill);
            productDetail.insertToDatabase();
        }

        selectedProducts.clear();

        updateNextBillID();

        for (BillUpdateObserver observer : billUpdateObservers) {
            observer.updateFromNewBillCreated(bill);
        }
    }

    @Override
    public long getChangeMoney() {
        return guestMoney - totalMoneyOfBill;
    }

    @Override
    public long getTotalMoney() {
        return totalMoneyOfBill;
    }

    @Override
    public Iterator<ProductSimpleModelInterface> getSearchByProductName(String searchText) {
        List<ProductSimpleModelInterface> result = new ArrayList<>();
        List<BoundExtractedResult<ProductSimpleModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.offeredProducts, product -> product.getName(),
                        AppConstant.SEARCH_SCORE_CUT_OFF);
        matches.forEach(match -> result.add(match.getReferent()));
        return result.iterator();
    }

    @Override
    public Iterator<ProductSimpleModelInterface> getAllProduct() {
        return offeredProducts.iterator();
    }

    @Override
    public boolean isBillHavingNoProduct() {
        return this.selectedProducts.isEmpty();
    }

    @Override
    public Iterator<ProductDetailModelInterface> getSelectedProductData() {
        return selectedProducts.iterator();
    }

    @Override
    public void addOfferedProductToBill(ProductSimpleModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }

        boolean found = false;

        for (ProductDetailModelInterface productDetail : selectedProducts) {
            if (productDetail.getProduct() == product) {
                productDetail.setAmount(productDetail.getAmount() + 1);

                billUpdateObservers.forEach(observer
                        -> observer.updateFromModifiedSelectedProduct(productDetail));

                found = true;

                break;
            }
        }

        if (!found) {
            ProductDetailModelInterface newProductDetail = new ProductDetailModel();
            newProductDetail.setProduct(product);
            selectedProducts.add(newProductDetail);

            billUpdateObservers.forEach(observer
                    -> observer.updateFromInsertedSelectedProduct(newProductDetail));
        }

        product.setAmount(product.getAmount() - 1);

        offeredProductUpdateObservers.forEach(observer
                -> observer.updateOfferedProductInfo(product));

        totalMoneyOfBill += product.getPrice();
        billUpdateObservers.forEach(observer -> observer.updateFromBillState());
    }

    @Override
    public void removeSeletedProductInBill(int selectedProductIndex) {
        if (selectedProductIndex < 0 || selectedProductIndex >= selectedProducts.size()) {
            throw new IndexOutOfBoundsException();
        }

        ProductDetailModelInterface productDetail = selectedProducts.get(selectedProductIndex);

        ProductSimpleModelInterface offeredProduct = productDetail.getProduct();

        // restore amount
        offeredProduct.setAmount(offeredProduct.getAmount() + productDetail.getAmount());

        offeredProductUpdateObservers.forEach(observer
                -> observer.updateOfferedProductInfo(offeredProduct));

        selectedProducts.remove(selectedProductIndex);

        billUpdateObservers.forEach(observer -> observer.updateFromDeletedSelectedProduct(selectedProductIndex));

        totalMoneyOfBill -= productDetail.getPrice();
        billUpdateObservers.forEach(observer -> observer.updateFromBillState());
    }

    @Override
    public void updateAmountOfSelectedProduct(int selectedProductIndex, int newAmount) {
        if (selectedProductIndex < 0 || selectedProductIndex >= selectedProducts.size()) {
            throw new IndexOutOfBoundsException();
        }

        ProductDetailModelInterface productDetail = selectedProducts.get(selectedProductIndex);

        ProductSimpleModelInterface offeredProduct = productDetail.getProduct();

        int originAmount = offeredProduct.getAmount() + productDetail.getAmount();

        offeredProduct.setAmount(originAmount - newAmount);

        offeredProductUpdateObservers.forEach(observer -> observer.updateOfferedProductInfo(offeredProduct));

        totalMoneyOfBill -= productDetail.getPrice();

        productDetail.setAmount(newAmount);

        billUpdateObservers.forEach(observer -> observer.updateFromModifiedSelectedProduct(productDetail));

        totalMoneyOfBill += productDetail.getPrice();
        billUpdateObservers.forEach(observer -> observer.updateFromBillState());
    }

    @Override
    public void clearSelectedProductData() {
        for (int i = 0; i < selectedProducts.size(); i++) {
            ProductDetailModelInterface productDetail = selectedProducts.get(i);
            ProductSimpleModelInterface offeredProduct = productDetail.getProduct();
            offeredProduct.setAmount(offeredProduct.getAmount() + productDetail.getAmount());
            offeredProductUpdateObservers.forEach(observer -> observer.updateOfferedProductInfo(offeredProduct));
        }

        selectedProducts.clear();

        totalMoneyOfBill = 0;
        billUpdateObservers.forEach(observer -> observer.updateFromBillState());
    }

    @Override
    public ProductDetailModelInterface getSelectedProductByIndex(int rowID) {
        if (rowID < 0 || rowID >= selectedProducts.size()) {
            throw new IndexOutOfBoundsException();
        }
        return selectedProducts.get(rowID);
    }

    @Override
    public int getOriginAmountOfProduct(ProductDetailModelInterface productDetail) {
        if (productDetail == null) {
            throw new NullPointerException();
        }
        int id = selectedProducts.indexOf(productDetail);
        if (id == -1) {
            return productDetail.getProduct().getAmount();
        }
        return productDetail.getProduct().getAmount() + selectedProducts.get(id).getAmount();
    }

}
