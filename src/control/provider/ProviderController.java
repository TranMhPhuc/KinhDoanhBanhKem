package control.provider;

import model.provider.ProviderModelInterface;
import view.function.product.ProviderPanel;

public class ProviderController implements ProviderControllerInterface {

    private volatile static ProviderController uniqueInstance;
    
    private ProviderModelInterface model;
    private ProviderPanel view;
    
    private ProviderController(ProviderModelInterface model) {
        this.model = model;
        this.view = ProviderPanel.getInstance(model, this);
    }
    
    public static ProviderControllerInterface getInstance(ProviderModelInterface model) {
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
    
}
