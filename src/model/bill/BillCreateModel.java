package model.bill;

import com.itextpdf.text.DocumentException;
import control.bill.create.BillPDF;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import model.bill.detail.ProductDetailModel;
import model.bill.detail.ProductDetailModelInterface;
import model.ingredient.IngredientManageModel;
import model.product.ProductSimpleModel;
import model.product.ProductSimpleModelInterface;
import model.user.UserModel;
import org.apache.commons.io.FilenameUtils;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import util.messages.Messages;
import view.bill.BillInsertedObserver;
import view.bill.BillUpdateObserver;

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
    private long totalProfit;
    private long guestMoney;

    private List<BillUpdateObserver> billUpdateObservers;
    private List<OfferedProductUpdateObserver> offeredProductUpdateObservers;
    private List<BillInsertedObserver> billInsertedObservers;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    public BillCreateModel() {
        offeredProducts = new ArrayList<>();
        selectedProducts = new ArrayList<>();
        billUpdateObservers = new ArrayList<>();
        offeredProductUpdateObservers = new ArrayList<>();
        billInsertedObservers = new ArrayList<>();
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
        if (observer == null) {
            throw new NullPointerException();
        }
        billUpdateObservers.add(observer);
    }

    @Override
    public void removeBillUpdateObserver(BillUpdateObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        int id = billUpdateObservers.indexOf(observer);
        if (id >= 0) {
            billUpdateObservers.remove(observer);
        }
    }

    @Override
    public void registerOfferedProductUpdateObserver(OfferedProductUpdateObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        offeredProductUpdateObservers.add(observer);
    }

    @Override
    public void removeOfferedProductUpdateObserver(OfferedProductUpdateObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        int id = offeredProductUpdateObservers.indexOf(observer);
        if (id >= 0) {
            offeredProductUpdateObservers.remove(id);
        }
    }

    @Override
    public void registerInsertedBillObserver(BillInsertedObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        billInsertedObservers.add(observer);
    }

    @Override
    public void removeInsertedBillObserver(BillInsertedObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        int id = billInsertedObservers.indexOf(observer);
        if (id >= 0) {
            billInsertedObservers.remove(id);
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
        bill.setProfit(totalProfit);
        bill.insertToDatabase();

        for (ProductDetailModelInterface productDetail : selectedProducts) {
            productDetail.setBill(bill);
            productDetail.insertToDatabase();
        }
        
        JFileChooser pdfFileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                File f = getSelectedFile();

                if (f.exists() && getDialogType() == JFileChooser.CUSTOM_DIALOG) {
                    String notify = f.getName() + Messages.getInstance().OTHERS_REPLACE_EXCEL;
                    int result = JOptionPane.showConfirmDialog(null, notify,
                            "BakeryMS", JOptionPane.YES_NO_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
        };
        pdfFileChooser.setDialogTitle("Export bill to pdf file");
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter(
                "PDF file (.pdf)", "pdf", "PDF");
        pdfFileChooser.setFileFilter(excelFilter);
        int choose = pdfFileChooser.showDialog(null, "Export");
        if (choose == JFileChooser.APPROVE_OPTION) {
            String absolutePath = pdfFileChooser.getSelectedFile()
                    .getAbsolutePath();
            String filePath = absolutePath.substring(0, absolutePath
                    .lastIndexOf(File.separator)) + File.separator;
            String fileName = FilenameUtils.removeExtension(pdfFileChooser
                    .getSelectedFile().getName());
            String result = filePath + fileName + ".pdf";
            try {
                BillPDF.exportBill(bill, selectedProducts.iterator(), result);
            } catch (DocumentException ex) {
                Logger.getLogger(BillCreateModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BillCreateModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        selectedProducts.clear();
        totalMoneyOfBill = 0L;
        totalProfit = 0L;
        
        updateNextBillID();

        billUpdateObservers.forEach(observer -> observer.updateFromNewBillCreated(bill));

        billInsertedObservers.forEach(observer -> observer.updateInsertedBillObserver());
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
        totalProfit += product.getProfit();
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
        totalProfit -= productDetail.getProfit();
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
        totalProfit -= productDetail.getProfit();;

        productDetail.setAmount(newAmount);

        billUpdateObservers.forEach(observer -> observer.updateFromModifiedSelectedProduct(productDetail));

        totalMoneyOfBill += productDetail.getPrice();
        totalProfit += productDetail.getProfit();
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

        totalMoneyOfBill = 0L;
        totalProfit = 0L;
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
