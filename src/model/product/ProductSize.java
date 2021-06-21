package model.product;

public enum ProductSize {
    S,
    M,
    L;

    public static ProductSize getProductSizeFromString(String name) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].name().equals(name)) {
                return values()[i];
            }
        }
        throw new IllegalArgumentException("Product size name doesn't exist.");
    }
}
