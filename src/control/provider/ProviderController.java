package control.provider;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import model.provider.ProviderManageModelInterface;
import model.provider.ProviderModel;
import model.provider.ProviderModelInterface;
import org.junit.Assert;
import util.common.string.StringUtil;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import util.excel.ExcelTransfer;
import util.messages.Messages;
import util.validator.EmailValidator;
import util.validator.PhoneValidator;
import view.provider.ProviderPanel;

public class ProviderController implements ProviderControllerInterface {
    
    private static final String SP_CHECK_DELETE_PROVIDER_CONDITION
            = "{? = call check_if_provider_provides_any_ingredient(?)}";
    
    private static Connection dbConnection;
    
    private List<ProviderModelInterface> searchList;
    
    private ProviderManageModelInterface providerManageModel;
    private ProviderPanel providerPanel;
    
    static {
        dbConnection = SQLServerConnection.getConnection();
    }
    
    public ProviderController(ProviderManageModelInterface providerManageModel) {
        this.searchList = new ArrayList<>();
        this.providerManageModel = providerManageModel;
    }
    
    private boolean isProviderNameValid(String providerName) {
        if (providerName.isEmpty()) {
            providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_NAME_EMPTY);
            return false;
        }
        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getName().equals(providerName)) {
                providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_NAME_EXISTS);
                return false;
            }
        }
        return true;
    }
    
    private boolean isProviderPhoneNumValid(String providerPhoneNum) {
        PhoneValidator.PhoneValidateResult phoneValidateResult
                = PhoneValidator.validate(providerPhoneNum);
        
        switch (phoneValidateResult) {
            case EMPTY: {
                this.providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_PHONE_NUMBER_EMPTY);
                return false;
            }
            case ERROR_FORMAT: {
                this.providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_PHONE_NUMBER_FORMAT);
                return false;
            }
            case INVALLID: {
                this.providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_PHONE_NUMBER_DIGITS_1 + PhoneValidator.getPhoneNumValid()
                        + Messages.getInstance().PROVIDER_PHONE_NUMBER_DIGITS_2);
                return false;
            }
        }
        
        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getPhoneNum().equals(providerPhoneNum)) {
                providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_PHONE_NUMBER_EXISTS);
                return false;
            }
        }
        return true;
    }
    
    private boolean isProviderEmailValid(String providerEmail) {
        EmailValidator.EmailValidateResult emailValidateResult
                = EmailValidator.validate(providerEmail);
        
        switch (emailValidateResult) {
            case EMPTY: {
                this.providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_EMAIL_EMPTY);
                return false;
            }
            case INVALLID: {
                this.providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_EMAIL_INVALID);
                return false;
            }
        }
        
        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getEmail().equals(providerEmail)) {
                providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_EMAIL_EXISTS);
                return false;
            }
        }
        return true;
    }
    
    private boolean isProviderAddressValid(String providerAddress) {
        if (providerAddress.isEmpty()) {
            providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_ADDRESS_EMPTY);
            return false;
        }
        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getAddress().equals(providerAddress)) {
                providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_ADDRESS_EXISTS);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void requestCreateProvider() {
        String providerIDText = this.providerPanel.getProviderIDtext();
        
        String providerName = this.providerPanel.getProviderName();
        providerName = StringUtil.getCapitalizeWord(providerName);
        
        if (!isProviderNameValid(providerName)) {
            return;
        }
        
        String providerEmail = this.providerPanel.getProviderEmai();
        
        if (!isProviderEmailValid(providerEmail)) {
            return;
        }
        
        String providerAddress = this.providerPanel.getProviderAddress();
        
        if (!isProviderAddressValid(providerAddress)) {
            return;
        }
        
        String providerPhoneNum = this.providerPanel.getProviderPhoneNum();
        
        if (!isProviderPhoneNumValid(providerPhoneNum)) {
            return;
        }
        
        ProviderModelInterface provider = new ProviderModel();
        provider.setProviderID(providerIDText);
        provider.setName(providerName);
        provider.setEmail(providerEmail);
        provider.setAddress(providerAddress);
        provider.setPhoneNum(providerPhoneNum);
        
        this.providerManageModel.addProvider(provider);
        
        this.providerPanel.exitEditState();
        this.providerPanel.showInfoMessage(Messages.getInstance().PROVIDER_INSERTED_SUCCESSFULLY);
        PhoneValidator.setValidDigitNum(10);
    }
    
    @Override
    public void requestUpdateProvider() {
        String providerIDText = this.providerPanel.getProviderIDtext();
        
        ProviderModelInterface provider = this.providerManageModel.getProviderByID(providerIDText);
        
        Assert.assertNotNull(provider);
        
        String providerName = this.providerPanel.getProviderName();
        providerName = StringUtil.getCapitalizeWord(providerName);
        
        if (!provider.getName().equals(providerName)) {
            if (!isProviderNameValid(providerName)) {
                return;
            }
        }
        
        String providerEmail = this.providerPanel.getProviderEmai();
        
        if (!provider.getEmail().equals(providerEmail)) {
            if (!isProviderEmailValid(providerEmail)) {
                return;
            }
        }
        
        String providerAddress = this.providerPanel.getProviderAddress();
        
        if (!provider.getAddress().equals(providerAddress)) {
            if (!isProviderAddressValid(providerAddress)) {
                return;
            }
        }
        
        String providerPhoneNum = this.providerPanel.getProviderPhoneNum();
        
        if (!providerPhoneNum.equals(provider.getPhoneNum())) {
            if (!isProviderPhoneNumValid(providerPhoneNum)) {
                return;
            }
        }
        
        provider.setName(providerName);
        provider.setAddress(providerAddress);
        provider.setEmail(providerEmail);
        provider.setPhoneNum(providerPhoneNum);
        
        this.providerManageModel.updateProvider(provider);
        
        this.providerPanel.exitEditState();
        this.providerPanel.showInfoMessage(Messages.getInstance().PROVIDER_UPDATED_SUCCESSFULLY);
        PhoneValidator.setValidDigitNum(10);
    }
    
    @Override
    public void requestRemoveProvider() {
        String providerIDText = this.providerPanel.getProviderIDtext();
        
        ProviderModelInterface provider = this.providerManageModel
                .getProviderByID(providerIDText);
        
        Assert.assertNotNull(provider);
        
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_CHECK_DELETE_PROVIDER_CONDITION);
            
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            provider.setKeyArg(2, ProviderModel.ID_HEADER, callableStatement);
            callableStatement.execute();
            
            boolean isProviderHavingIngredient = callableStatement.getBoolean(1);
            
            callableStatement.close();
            
            if (isProviderHavingIngredient) {
                providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_CANT_REMOVE);
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.providerManageModel.removeProvider(provider);
        
        this.searchList.remove(provider);
        
        this.providerPanel.showInfoMessage(Messages.getInstance().PROVIDER_REMOVED_SUCCESSFULLY);
    }
    
    @Override
    public void requestExportExcel() {
        if (providerPanel.getTableProviderRowCount() == 0) {
            providerPanel.showErrorMessage(Messages.getInstance().PROVIDER_TABLE_EMPTY);
        } else {
            ExcelTransfer.exportTableToExcel(providerPanel.getTableProvider());
        }
    }
    
    @Override
    public Iterator<ProviderModelInterface> getAllProviderData() {
        Iterator<ProviderModelInterface> iterator = this.providerManageModel.getAllProviderData();
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
        
    }
    
    @Override
    public Iterator<ProviderModelInterface> getProviderBySearchName(String searchText) {
        Iterator<ProviderModelInterface> iterator = this.providerManageModel.getProviderSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }
    
    @Override
    public void requestShowProviderInfo() {
        if (providerPanel.getEditState() == ProviderPanel.EditState.ADD) {
            return;
        }
        
        int rowID = this.providerPanel.getSelectedRow();
        if (rowID == -1) {
            return;
        }
        if (rowID >= searchList.size()) {
            throw new IllegalArgumentException("Row index is not in bound");
        }
        ProviderModelInterface provider = this.searchList.get(rowID);
        this.providerPanel.showProviderInfo(provider);
    }
    
    @Override
    public boolean isSearchMatching(String searchText, ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null.");
        }
        boolean ret = searchText.isEmpty()
                || (FuzzySearch.weightedRatio(searchText, provider.getName())
                >= AppConstant.SEARCH_SCORE_CUT_OFF);
        if (ret) {
            this.searchList.add(provider);
        }
        return ret;
    }
    
    @Override
    public boolean deleteProviderInSearchList(ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null.");
        }
        int id = this.searchList.indexOf(provider);
        if (id >= 0) {
            this.searchList.remove(id);
            return true;
        }
        return false;
    }
    
    @Override
    public void setProviderPanelView(ProviderPanel providerPanel) {
        if (providerPanel == null) {
            throw new NullPointerException();
        }
        this.providerPanel = providerPanel;
        providerPanel.setProviderController(this);
        providerPanel.setProviderManageModel(providerManageModel);
    }
    
    @Override
    public void requestCreateTemplateExcel() {
        ExcelTransfer.createExcelFileTemplate(providerPanel.getTableProvider());
    }
    
    @Override
    public boolean canCloseProviderManagePanel() {
        if (providerPanel.getEditState() == ProviderPanel.EditState.ADD
                || providerPanel.getEditState() == ProviderPanel.EditState.MODIFY) {
            int ret = JOptionPane.showConfirmDialog(providerPanel.getMainFrame(),
                    Messages.getInstance().PROVIDER_CANCEL_EDITING,
                    "BakeryMS",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_WARNING);
            if (ret == JOptionPane.YES_OPTION) {
                providerPanel.exitEditState();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
    
    public void requestChangePhoneNumConstraint() {
        String inputText = (String) JOptionPane.showInputDialog(providerPanel
                .getMainFrame(), Messages.getInstance().EMPLOYEE_CUSTOM_PHONE_NUMBER_CONS,
                "BakeryMS", JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (inputText != null && !inputText.isEmpty()) {
            int num;
            try {
                num = Integer.parseInt(inputText);
                if (num < 0) {
                    throw new NumberFormatException("Input value is invalid");
                }
            } catch (NumberFormatException ex) {
                providerPanel.showErrorMessage(Messages.getInstance().EMPLOYEE_INVALID_CUSTOM_PHONE_NUMBER_CONS_NUM);
                return;
            }
            PhoneValidator.setValidDigitNum(num);
        }
    }
    
}
