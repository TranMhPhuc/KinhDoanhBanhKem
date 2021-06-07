package control.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import static model.provider.ProviderDataStorage.FIND_NAME_SCORE_CUT_OFF;
import model.provider.ProviderManageModelInterface;
import model.provider.ProviderModel;
import model.provider.ProviderModelInterface;
import org.junit.Assert;
import util.validator.EmailValidator;
import util.validator.PhoneValidator;
import view.function.provider.ProviderPanel;

public class ProviderController implements ProviderControllerInterface {

    private volatile static ProviderController uniqueInstance;

    private List<ProviderModelInterface> searchList;

    private ProviderManageModelInterface model;
    private ProviderPanel view;

    private ProviderController(ProviderManageModelInterface model) {
        this.searchList = new ArrayList<>();

        this.model = model;
        this.view = ProviderPanel.getInstance(model, this);
    }

    public static ProviderControllerInterface getInstance(ProviderManageModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (ProviderController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProviderController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static ProviderController getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    @Override
    public void requestCreateProvider() {
        String providerIDText = this.view.getProviderIDtext();

        String providerName = this.view.getProviderName();
        if (providerName.isEmpty()) {
            this.view.showErrorMessage("Provider name is required.");
            return;
        }
        if (this.model.isProviderNameExist(providerName)) {
            this.view.showErrorMessage("Provider name is existed.");
            return;
        }

        String providerEmail = this.view.getProviderEmai();
        EmailValidator.EmailValidateResult emailValidateResult
                = EmailValidator.validate(providerEmail);
        switch (emailValidateResult) {
            case EMPTY: {
                this.view.showErrorMessage("Provider email is required.");
                return;
            }
            case INVALLID: {
                this.view.showErrorMessage("Provider email is invallid.");
                return;
            }
        }
        if (this.model.isProviderEmailExist(providerEmail)) {
            this.view.showErrorMessage("Provider email is existed.");
            return;
        }

        String providerAddress = this.view.getProviderAddress();
        if (providerAddress.isEmpty()) {
            this.view.showErrorMessage("Provider address is required.");
            return;
        }
        if (this.model.isProviderAddressExist(providerAddress)) {
            this.view.showErrorMessage("Provider address is existed.");
            return;
        }

        String providerPhoneNum = this.view.getProviderPhoneNum();
        PhoneValidator.PhoneValidateResult phoneValidateResult
                = PhoneValidator.validate(providerPhoneNum);
        switch (phoneValidateResult) {
            case EMPTY: {
                this.view.showErrorMessage("Provider phone number is empty.");
                return;
            }
            case ERROR_FORMAT: {
                this.view.showErrorMessage("Please enter provider phone number in number format.");
                return;
            }
            case INVALLID: {
                this.view.showErrorMessage("Provider number should be in 10 numbers.");
                return;
            }
        }
        if (this.model.isProviderPhoneNumExist(providerPhoneNum)) {
            this.view.showErrorMessage("Provider phone number is existed.");
            return;
        }

        ProviderModelInterface provider = new ProviderModel();
        provider.setProviderID(providerIDText);
        provider.setName(providerName);
        provider.setEmail(providerEmail);
        provider.setAddress(providerAddress);
        provider.setPhoneNum(providerPhoneNum);

        this.model.addNewProvider(provider);

        this.view.exitEditState();
        this.view.showInfoMessage("Insert new provider successfullly!");
    }

    @Override
    public void requestUpdateProvider() {
        String providerIDText = this.view.getProviderIDtext();

        ProviderModelInterface provider = this.model.getProvider(providerIDText);

        Assert.assertNotNull(provider);

        String providerName = this.view.getProviderName();
        if (providerName.isEmpty()) {
            this.view.showErrorMessage("Provider name is required.");
            return;
        }
        if (!provider.getName().equals(providerName)) {
            if (this.model.isProviderNameExist(providerName)) {
                this.view.showErrorMessage("Provider name is existed.");
                return;
            }
        }

        String providerEmail = this.view.getProviderEmai();

        if (!provider.getEmail().equals(providerEmail)) {
            EmailValidator.EmailValidateResult emailValidateResult
                    = EmailValidator.validate(providerEmail);
            switch (emailValidateResult) {
                case EMPTY: {
                    this.view.showErrorMessage("Provider email is required.");
                    return;
                }
                case INVALLID: {
                    this.view.showErrorMessage("Provider email is invallid.");
                    return;
                }
            }
            if (this.model.isProviderEmailExist(providerEmail)) {
                this.view.showErrorMessage("Provider email is existed.");
                return;
            }
        }

        String providerAddress = this.view.getProviderAddress();

        if (!provider.getAddress().equals(providerAddress)) {
            if (providerAddress.isEmpty()) {
                this.view.showErrorMessage("Provider address is required.");
                return;
            }
            if (this.model.isProviderAddressExist(providerAddress)) {
                this.view.showErrorMessage("Provider address is existed.");
                return;
            }
        }

        String providerPhoneNum = this.view.getProviderPhoneNum();

        if (!provider.getPhoneNum().equals(providerPhoneNum)) {
            PhoneValidator.PhoneValidateResult phoneValidateResult
                    = PhoneValidator.validate(providerPhoneNum);
            switch (phoneValidateResult) {
                case EMPTY: {
                    this.view.showErrorMessage("Provider phone number is empty.");
                    return;
                }
                case ERROR_FORMAT: {
                    this.view.showErrorMessage("Please enter provider phone number in number format.");
                    return;
                }
                case INVALLID: {
                    this.view.showErrorMessage("Provider number should be in 10 numbers.");
                    return;
                }
            }
            if (this.model.isProviderPhoneNumExist(providerPhoneNum)) {
                this.view.showErrorMessage("Provider phone number is existed.");
                return;
            }
        }

        provider.setName(providerName);
        provider.setAddress(providerAddress);
        provider.setEmail(providerEmail);
        provider.setPhoneNum(providerPhoneNum);

        this.model.updateProvider(provider);

        this.view.exitEditState();
        this.view.showInfoMessage("Update provider data successfullly!");
    }

    @Override
    public void requestRemoveProvider() {
    }

    @Override
    public void requestImportExcel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void requestExportExcel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<ProviderModelInterface> getAllProviderData() {
        Iterator<ProviderModelInterface> iterator = this.model.getAllProvider();
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();

    }

    @Override
    public Iterator<ProviderModelInterface> getProviderBySearchName(String searchText) {
        Iterator<ProviderModelInterface> iterator = this.model.getProviderSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public void requestShowProviderInfo() {
        int rowID = this.view.getSelectedRow();
        if (rowID == -1) {
            return;
        }
        if (rowID >= searchList.size()) {
            throw new IllegalArgumentException("Row index is not in bound");
        }
        ProviderModelInterface provider = this.searchList.get(rowID);
        this.view.showProviderInfo(provider);
    }

    private boolean havingMatchedSearchName(String searchText, ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null.");
        }
        return (FuzzySearch.ratio(searchText, provider.getName()) >= FIND_NAME_SCORE_CUT_OFF);
    }

    @Override
    public boolean insertToSearchListByMatchingName(String searchText, ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null.");
        }
        boolean ret = (FuzzySearch.ratio(searchText, provider.getName()) >= FIND_NAME_SCORE_CUT_OFF);
        if (ret) {
            this.searchList.add(provider);
        }
        return ret;
    }

}
