package control.bill.create;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import model.bill.detail.ProductDetailModelInterface;
import model.product.ProductModelInterface;
import model.product.ProductSimpleModelInterface;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import view.dialog.BillExportDialog;
import view.bill.BillCreatePanel;
import view.dialog.AmountDialog;
import model.bill.BillCreateModelInterface;

public class BillCreateController implements BillCreateControllerInterface {

    private BillCreateModelInterface billManageModel;
    private BillCreatePanel billCreatePanel;

    List<ProductSimpleModelInterface> productSearchList;

    private BillExportDialog dialogBillExport;
    private AmountDialog dialogAmount;

    public BillCreateController(BillCreateModelInterface billManageModel) {
        productSearchList = new ArrayList<>();
        this.billManageModel = billManageModel;
        billManageModel.registerOfferedProductUpdateObserver(this);
    }

    public void setBillCreatePanel(BillCreatePanel billCreatePanel) {
        if (billCreatePanel == null) {
            throw new NullPointerException();
        }
        this.billCreatePanel = billCreatePanel;
        billCreatePanel.setBillCreateController(this);
        billCreatePanel.setBillManageModel(billManageModel);
    }

    @Override
    public Iterator<ProductSimpleModelInterface> getProductSearch(String searchText) {
        productSearchList.clear();
        Iterator<ProductSimpleModelInterface> iterator = billManageModel
                .getSearchByProductName(searchText);
        while (iterator.hasNext()) {
            productSearchList.add(iterator.next());
        }
        return productSearchList.iterator();
    }

    @Override
    public Iterator<ProductSimpleModelInterface> getAllProduct() {
        productSearchList.clear();
        Iterator<ProductSimpleModelInterface> iterator = billManageModel.getAllProduct();
        while (iterator.hasNext()) {
            productSearchList.add(iterator.next());
        }
        return productSearchList.iterator();
    }

    @Override
    public void requestClearProductSearch() {
        this.billCreatePanel.setSearchProductText("");
    }

    @Override
    public void requestChooseOfferedProduct() {
        int rowID = billCreatePanel.getOfferedProductTableSelectedRowIndex();
        if (rowID == -1) {
            billCreatePanel.showInfoMessage("You should choose one product first.");
            return;
        }
        ProductSimpleModelInterface offeredProduct = productSearchList.get(rowID);
        if (offeredProduct.getAmount() == 0) {
            billCreatePanel.showErrorMessage("Product " + offeredProduct.getName() + " - "
                    + offeredProduct.getSize().toString() + " is out of stock.");
            return;
        }
        billManageModel.addOfferedProductToBill(productSearchList.get(rowID));
    }

    @Override
    public void requestRemoveSelectedProduct() {
        int rowID = billCreatePanel.getSelectedProductTableSelectedRowIndex();
        if (rowID == -1) {
            billCreatePanel.showErrorMessage("You should choose one selected product first.");
            return;
        }
        billManageModel.removeSeletedProductInBill(rowID);
    }

    @Override
    public void requestClearTableSelectedProduct() {
        if (billManageModel.isBillHavingNoProduct()) {
            billCreatePanel.showErrorMessage("Bill has no product.");
            return;
        }
        billManageModel.clearSelectedProductData();
    }

    @Override
    public void requestEditSelectedProductAmount() {
        int rowID = billCreatePanel.getSelectedProductTableSelectedRowIndex();
        if (rowID == -1) {
            billCreatePanel.showErrorMessage("You should choose one selected product first.");
            return;
        }
        if (dialogAmount == null) {
            dialogAmount = new AmountDialog(billCreatePanel.getMaiFrame(), true, this);
        }
        
        int selectedProductedAmount = billManageModel.getSelectedProductByIndex(rowID).getAmount();
        
        dialogAmount.setAmountInput(selectedProductedAmount);
        
        dialogAmount.setVisible(true);
    }

    @Override
    public void validateAmountInputFromDialog() {
        int newAmount = dialogAmount.getAmountInput();
        
        int rowID = billCreatePanel.getSelectedProductTableSelectedRowIndex();
        
        ProductDetailModelInterface selectedProduct = billManageModel.getSelectedProductByIndex(rowID);
        
        int originAmount = billManageModel.getOriginAmountOfProduct(selectedProduct);
        
        if (newAmount > originAmount) {
            String messageFormat = "Product %s - %s has not enough amount";
            ProductSimpleModelInterface offeredProduct = selectedProduct.getProduct();
            JOptionPane.showMessageDialog(dialogAmount, 
                    String.format(messageFormat, offeredProduct.getName(), offeredProduct.getSize().toString()), 
                    null, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        this.billManageModel.updateAmountOfSelectedProduct(rowID, newAmount);
        
        dialogAmount.dispose();
    }

    @Override
    public void requestExportBill() {
        if (billManageModel.isBillHavingNoProduct()) {
            billCreatePanel.showErrorMessage("Bill has no product!");
            return;
        }

        if (dialogBillExport == null) {
            dialogBillExport = new BillExportDialog(billCreatePanel.getMaiFrame(),
                    true, billManageModel, this);
        }

        dialogBillExport.setBillIDText(billManageModel.getNextBillIDText());
        dialogBillExport.setBillTotalMoney(String.valueOf(billManageModel.getTotalMoney()));

        resetGuestMoneyInputInDialog();

        dialogBillExport.setVisible(true);
    }

    private void resetGuestMoneyInputInDialog() {
        dialogBillExport.setGuestMoneyText("");
        dialogBillExport.setChangeMoneyText("");
        dialogBillExport.setLabelErrorText("");
    }

    @Override
    public void validateGuestMoney() {
        String guestMoneyTextInput = dialogBillExport.getGuestMoneyInput();

        if (guestMoneyTextInput.isEmpty()) {
            dialogBillExport.setBtnContinueEnable(false);
            dialogBillExport.setChangeMoneyText("");
            dialogBillExport.setLabelErrorText("");
            return;
        }

        long guestMoney;

        try {
            guestMoney = Long.parseLong(guestMoneyTextInput);
        } catch (NumberFormatException ex) {
            dialogBillExport.setBtnContinueEnable(false);
            dialogBillExport.setChangeMoneyText("");
            dialogBillExport.setLabelErrorText("Money input format error");
            return;
        }

        long totalMoney = billManageModel.getTotalMoney();

        if (guestMoney < totalMoney) {
            dialogBillExport.setBtnContinueEnable(false);
            dialogBillExport.setChangeMoneyText("");
            dialogBillExport.setLabelErrorText("Guest money is insuccint");
            return;
        }

        billManageModel.setGuestMoney(guestMoney);

        dialogBillExport.setBtnContinueEnable(true);
        billManageModel.setGuestMoney(guestMoney);
        dialogBillExport.setLabelErrorText("");
    }

    @Override
    public void exportBill() {
        this.billManageModel.createBill();
        dialogBillExport.showInfoMessage("Create bill successfully.");
        dialogBillExport.dispose();
    }

    @Override
    public void updateOfferedProductInfo(ProductSimpleModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        int id = productSearchList.indexOf(product);
        if (id == -1) {
            // offer product not found in search list => no need to update
            return;
        }
        billCreatePanel.updateOfferedProductInfo(id, product);
    }

}
