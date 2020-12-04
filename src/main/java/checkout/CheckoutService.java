package checkout;

import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Service to handle the overall Checkout System.
 * Can view and call the Basket Service, and will perform price calculations for a
 * given List of Items, while delegating to the Promotion Service to apply Promotions
 * currently loaded into the System.
 *
 * @author Brooke Godbold
 */
@Component("checkout")
public class CheckoutService {

    @Autowired
    PromotionService promotionService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    BasketService basketService;

    public CheckoutService() { }

    public boolean scan(Integer productCode) {
        Optional<Item> matchedInventory = inventoryService.getInventory().stream()
                .filter(inventoryItem -> inventoryItem.getProductCode().equals(productCode))
                .findFirst();
        if(matchedInventory.isPresent()) {
            basketService.addToBasket((Item) matchedInventory.get().clone());
            return true;
        }
        return false;
    }

    public ArrayList<Item> getBasketProducts() {
        return basketService.getBasketProducts();
    }

    public ArrayList<Item> getInventory() {
        return inventoryService.getInventory();
    }

    public Double total() {
        basketService.setBasketProducts(
                promotionService.calculateItemPromotions(basketService.getBasketProducts(), promotionService.getPromotions()
        ));
        double price = getTotalPrice();
        price = promotionService.calculatePricePromotions(price, promotionService.getPromotions());
        basketService.clearBasket();

        return price;
    }

    public double getTotalPrice() {
        double price = 0;
        for(Item product : basketService.getBasketProducts()) {
            price += product.getPrice();
        }
        return price;
    }
}
