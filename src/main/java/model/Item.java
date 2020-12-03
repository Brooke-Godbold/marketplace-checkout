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

    @Override
    public Object clone() {
        Item item = null;
        try {
            item = (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            item = new Item(this.getProductCode(), this.getName(), this.getPrice());
        }
        return item;
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
