package control.bill;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.bill.BillManageModelInterface;
import model.product.ProductDataStorage;
import model.product.ProductDataStorageInterface;
import model.product.ProductModelInterface;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import view.dialog.BillExportDialog;
import view.function.bill.BillCreatePanel;
import view.function.bill.BillHistoryPanel;
import view.main.MainFrame;

public class BillController implements BillControllerInterface {

    private volatile static BillController uniqueInstance;
    private static ProductDataStorageInterface productDataStorage;

    private BillManageModelInterface model;
    private BillCreatePanel billCreatePanel;
    private BillHistoryPanel billHistoryPanel;

    private List<Pair<ProductModelInterface, Integer>> selectedProducts;

    private Map<ProductModelInterface, Integer> remainAmountOfProducts;

    private int totalMoneyOfBill;
    private int guestMoney;

    private BillExportDialog billExportDialog;

    static {
        productDataStorage = ProductDataStorage.getInstance();
    }

    private BillController(BillManageModelInterface model) {
        this.model = model;
        this.selectedProducts = new ArrayList<>();
        this.totalMoneyOfBill = 0;

        remainAmountOfProducts = new HashMap<>();

        Iterator<ProductModelInterface> iterator = productDataStorage.createIterator();
        while (iterator.hasNext()) {
            ProductModelInterface product = iterator.next();
            remainAmountOfProducts.put(product, product.getAmount());
        }

        this.billCreatePanel = BillCreatePanel.getInstance(model, this);
        this.billHistoryPanel = BillHistoryPanel.getInstance(this);
    }

    public static BillControllerInterface getInstance(BillManageModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (BillController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new BillController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static BillController getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    @Override
    public String getNewBillID() {
        String newBillID = this.model.getNextBillID();
        return newBillID;
    }

    @Override
    public List<Pair<ProductModelInterface, Integer>> getProductSearch(String searchText) {
        List<Pair<ProductModelInterface, Integer>> results = new ArrayList<>();
        Iterator<ProductModelInterface> iterator = productDataStorage
                .searchProductByName(searchText).iterator();
        while (iterator.hasNext()) {
            ProductModelInterface product = iterator.next();
            results.add(new ImmutablePair<>(product, remainAmountOfProducts.get(product)));
        }
        return results;
    }

    @Override
    public void requestClearSearch() {
        this.billCreatePanel.setSearchProductText("");
    }

    @Override
    public void requestChooseProduct(int rowID) {
        int leftAmount = this.billCreatePanel.getLeftAmountProductOffer(rowID);
        if (leftAmount == 0) {
            this.billCreatePanel.showErrorMessage("Product is out of stock.");
        } else {

            // get index of product in store
            ProductModelInterface product = null;

            // Check product already in select table or not
            String productIDText = this.billCreatePanel.getIDProductOffer(rowID);
            int index = getIndexOfStoredProduct(productIDText);

            if (index != -1) {
                // Get current amount of product
                int amount = selectedProducts.get(index).getValue();

                // Increase amount by 1
                amount += 1;
                selectedProducts.get(index).setValue(amount);
                this.billCreatePanel.updateAmountProductSelect(index, amount);

                // Increase total price of product in table select
                product = selectedProducts.get(index).getKey();
                int price = product.getPrice() * amount;
                this.billCreatePanel.updatePriceProductSelect(index, price);
            } else {
                // Add product in table select as a new row
                product = productDataStorage.getProduct(productIDText);
                this.selectedProducts.add(new MutablePair<>(product, 1));
                this.billCreatePanel.addRowTableSelect(product);
            }

            // Decrease amount by 1 in offer table
            this.billCreatePanel.updateLeftAmountProductOffer(rowID, leftAmount - 1);

            // Decreate amount by 1 in mapper remain amount of product
            this.remainAmountOfProducts.replace(product, leftAmount - 1);

            // Increase total money by product price
            this.totalMoneyOfBill += product.getPrice();
            this.billCreatePanel.setTotalMoneyText(String.valueOf(this.totalMoneyOfBill));
        }
    }

    private int getIndexOfStoredProduct(String productIDText) {
        for (int i = 0; i < selectedProducts.size(); i++) {
            if (selectedProducts.get(i).getKey().getProductIDText().equals(productIDText)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void requestRemoveSelectedProduct(int rowID) {
        // Remove product in table select
        this.billCreatePanel.removeRowTableSelect(rowID);

        // Update record in table offer if it appears (it doesn't appear if 
        // user searches something).
        ProductModelInterface product = this.selectedProducts.get(rowID).getKey();

        int indexOfTableOffer = this.billCreatePanel.getRowIndexTableOffer(product.getProductIDText());
        if (indexOfTableOffer != -1) {
            int initAmount = product.getAmount();
            this.billCreatePanel.updateLeftAmountProductOffer(indexOfTableOffer, initAmount);
        }

        // Update total money
        int productPrice = product.getPrice();
        int amount = this.selectedProducts.get(rowID).getValue();
        int price = productPrice * amount;
        this.totalMoneyOfBill -= price;
        this.billCreatePanel.setTotalMoneyText(String.valueOf(this.totalMoneyOfBill));

        // Update remain amount of product in mapper
        this.remainAmountOfProducts.replace(product, product.getAmount());

        // Remove product in store
        this.selectedProducts.remove(rowID);
    }

    @Override
    public void requestClearTableSelect() {
        // XXX
    }

    @Override
    public void requestExportBill() {
        // Show dialog to enter guest money
        this.billExportDialog = new BillExportDialog(MainFrame.getInstance(), true, this);
        this.billExportDialog.setVisible(true);
    }

    @Override
    public void inputGuestMoney(String guestMoneyInput) {
        this.billExportDialog.setLabelErrorText("");
        if (guestMoneyInput.isEmpty()) {
            this.billExportDialog.setBtnContinueEnable(false);
            this.billExportDialog.setChangeMoneyText("");
        } else {
            int guestMoney = 0;
            try {
                guestMoney = Integer.parseInt(guestMoneyInput);
            } catch (NumberFormatException ex) {
                this.billExportDialog.setLabelErrorText("Please enter money in number!");
                this.billExportDialog.setBtnContinueEnable(false);
                return;
            }
            int changeMoney = (guestMoney - this.totalMoneyOfBill);
            if (changeMoney < 0) {
                this.billExportDialog.setLabelErrorText("Payment is not succint!");
                this.billExportDialog.setChangeMoneyText("0");
                this.billExportDialog.setBtnContinueEnable(false);
            } else {
                this.billExportDialog.setChangeMoneyText(String.valueOf(changeMoney));
                this.guestMoney = guestMoney;
                this.billExportDialog.setBtnContinueEnable(true);
            }
        }
    }

    @Override
    public List<Pair<ProductModelInterface, Integer>> getRemainProduct() {
        List<Pair<ProductModelInterface, Integer>> ret = new ArrayList<>();
        Iterator<ProductModelInterface> iterator = productDataStorage.createIterator();
        while (iterator.hasNext()) {
            ProductModelInterface product = iterator.next();
            Assert.assertNotNull(this.remainAmountOfProducts);
            ret.add(new ImmutablePair<>(product, this.remainAmountOfProducts.get(product)));
        }
        return ret;
    }

    @Override
    public void exportBill() {
        int changeMoney = this.guestMoney - this.totalMoneyOfBill;
        // Insert new bill to database
        this.model.prepareBill();
        this.model.setBillPayment(this.totalMoneyOfBill);
        this.model.setBillGuestMoney(this.guestMoney);
        this.model.setBillChangeMoney(changeMoney);
        this.model.setBillEmployee(MainFrame.getInstance().getModel().getImpl());
        this.model.setBillDateTimeExport(Timestamp.valueOf(LocalDateTime.now()));
        
        System.out.println("Guest money: " + this.guestMoney + ", total: " + this.totalMoneyOfBill);
        
        this.model.exportBill();

        // If success, confirm to view in PDF format
        // XXX
        // If sucess, update remain amount of product data
        for (Pair<ProductModelInterface, Integer> element : selectedProducts) {
            ProductModelInterface product = element.getKey();
            product.setAmount(product.getAmount() - element.getValue());
        }

        // Clear stored product
        this.selectedProducts.clear();

        // Reset view
        this.billCreatePanel.resetAll();
    }

}
