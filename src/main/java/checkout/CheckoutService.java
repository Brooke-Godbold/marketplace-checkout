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
                basketService.addToBasket(item);
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
        double price = 0;

        basketService.setBasketProducts(
                promotionService.calculateItemPromotions(basketService.getBasketProducts(), promotionService.getPromotions()
        ));
        price = getTotalPrice(price);
        price = promotionService.calculatePricePromotions(price, promotionService.getPromotions());
        clearBasket();

        return price;
    }

    public double getTotalPrice(double price) {
        for(Item product : basketService.getBasketProducts()) {
            price += product.getPrice();
        }
        return price;
    }

    public void clearBasket() {
        basketService.setBasketProducts(
                new ArrayList<>()
        );
    }
}
