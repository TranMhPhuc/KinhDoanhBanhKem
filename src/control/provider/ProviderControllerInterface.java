package control.provider;

import java.util.Iterator;
import model.provider.ProviderModelInterface;

public interface ProviderControllerInterface {

    void requestCreateProvider();

    void requestUpdateProvider();

    void requestRemoveProvider();

    void requestImportExcel();

    void requestExportExcel();
    
    void requestShowProviderInfo();
    
    boolean insertToSearchListByMatchingName(String searchText, ProviderModelInterface provider);

    Iterator<ProviderModelInterface> getAllProviderData();

    Iterator<ProviderModelInterface> getProviderBySearchName(String searchText);
    
}
