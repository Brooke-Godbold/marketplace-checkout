package model;

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
