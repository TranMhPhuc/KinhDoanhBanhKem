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
            providerPanel.showErrorMessage("Provider name must not be empty.");
            return false;
        }
        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getName().equals(providerName)) {
                providerPanel.showErrorMessage("Provider name is already existed.");
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
                this.providerPanel.showErrorMessage("Provider phone number is empty.");
                return false;
            }
            case ERROR_FORMAT: {
                this.providerPanel.showErrorMessage("Please enter provider phone number in number format.");
                return false;
            }
            case INVALLID: {
                this.providerPanel.showErrorMessage("Provider number should be in 10 numbers.");
                return false;
            }
        }

        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getPhoneNum().equals(providerPhoneNum)) {
                providerPanel.showErrorMessage("Provider phone num is already existed.");
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
                this.providerPanel.showErrorMessage("Provider email must be not empty.");
                return false;
            }
            case INVALLID: {
                this.providerPanel.showErrorMessage("Provider email is invallid.");
                return false;
            }
        }

        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getEmail().equals(providerEmail)) {
                providerPanel.showErrorMessage("Provider email is already existed.");
                return false;
            }
        }
        return true;
    }

    private boolean isProviderAddressValid(String providerAddress) {
        if (providerAddress.isEmpty()) {
            providerPanel.showErrorMessage("Provider address must be not empty.");
            return false;
        }
        Iterator<ProviderModelInterface> iterator = this.providerManageModel
                .getAllProviderData();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            if (provider.getAddress().equals(providerAddress)) {
                providerPanel.showErrorMessage("Provider address is already existed.");
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
        this.providerPanel.showInfoMessage("Insert new provider successfullly!");
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

        if (!provider.getPhoneNum().equals(providerPhoneNum)) {
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
        this.providerPanel.showInfoMessage("Update provider data successfully.");
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
                providerPanel.showErrorMessage("Can't delete provider having "
                        + "existed ingredient.");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.providerManageModel.removeProvider(provider);

        this.searchList.remove(provider);

        this.providerPanel.showInfoMessage("Delete provider successfully.");
    }

    @Override
    public void requestExportExcel() {
        if (providerPanel.getTableProviderRowCount() == 0) {
            providerPanel.showErrorMessage("Table provider data is empty.");
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
                    "Cancel editing provider?",
                    "Cancel editing provider confirm dialog",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) {
                providerPanel.exitEditState();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

}
