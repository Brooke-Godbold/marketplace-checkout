package checkout;

import model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Service which handles the state of the Basket.
 * Stores the Items which are currently in the Basket, and can manipulate and return
 * this List as required.
 *
 * @author Brooke Godbold
 */
@Component("basketService")
public class BasketService {

    private ArrayList<Item> basketProducts;

    public BasketService() {
        basketProducts = new ArrayList<>();
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

    public void clearBasket() {
        basketProducts = new ArrayList<>();
    }
}
