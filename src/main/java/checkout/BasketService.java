package checkout;

import model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("basketService")
public class BasketService {

    private ArrayList<Item> basketProducts;

    public BasketService() {
        basketProducts = new ArrayList<Item>();
    }

    public void addToBasket(Item item) {
        basketProducts.add(item);
    }

    public ArrayList<Item> getBasketProducts() {
        return basketProducts;
    }

    public void setBasketProducts(ArrayList<Item> newBasket) {
        this.basketProducts = newBasket;
    }
}
