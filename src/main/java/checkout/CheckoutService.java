package checkout;

import model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
        for(Item item : inventoryService.getInventory()) {
            if (item.getProductCode().equals(productCode)) {
                basketService.addToBasket((Item) item.clone());
                return true;
            }
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
