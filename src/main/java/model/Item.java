package model;

public class Item {

    private final Integer productCode;
    private final String name;
    private Double price;

    public Item(Integer productCode, String name, Double price) {
        this.productCode = productCode;
        this.name = name;
        this.price = price;
    }

    public Integer getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
