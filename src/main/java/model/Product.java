package model;

/**
 * Represents a Product as a Product Code.
 * @author Brooke Godbold
 */
public class Product {

    private int productCode;

    public Product() { }

    public Product(int productCode) {
        this.productCode = productCode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }
}
