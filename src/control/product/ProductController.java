package control.product;

import model.product.ProductModelInterface;
import view.function.product.ProductPanel;

public class ProductController implements ProductControllerInterface {

    private volatile static ProductController uniqueInstance;

    private ProductModelInterface model;
    private ProductPanel view;

    private ProductController(ProductModelInterface model) {
        this.model = model;
        this.view = ProductPanel.getInstance(model, this);
    }

    public static final ProductControllerInterface getInstance(ProductModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (ProductController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProductController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static final ProductControllerInterface getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }
}
