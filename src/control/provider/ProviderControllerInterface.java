package control.provider;

import java.util.Iterator;
import model.provider.ProviderModelInterface;
import view.provider.ProviderPanel;

public interface ProviderControllerInterface {

    void setProviderPanelView(ProviderPanel providerPanel);
    
    void requestCreateProvider();

    void requestUpdateProvider();

    void requestRemoveProvider();

    void requestExportExcel();
    
    void requestCreateTemplateExcel();
    
    void requestShowProviderInfo();
    
    boolean isSearchMatching(String searchText, ProviderModelInterface provider);
    
    boolean deleteProviderInSearchList(ProviderModelInterface provider);
    
    Iterator<ProviderModelInterface> getAllProviderData();

    Iterator<ProviderModelInterface> getProviderBySearchName(String searchText);
    
    boolean canCloseProviderManagePanel();
}
