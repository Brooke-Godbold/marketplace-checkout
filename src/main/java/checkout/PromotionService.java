package checkout;

import model.Item;
import org.springframework.stereotype.Component;
import utils.PromotionUtils;

import java.util.ArrayList;

@Component("promotions")
public class PromotionService {

    private ArrayList<Boolean> promotions;

    public PromotionService() {
        promotions = new ArrayList<Boolean>(){{
            add(PromotionUtils.TEN_PERCENT_OFF);
            add(PromotionUtils.TWO_OR_MORE_TRAVEL_CARD_HOLDERS);
        }};
    }

    public ArrayList<Item> calculateItemPromotions(ArrayList<Item> products, ArrayList<Boolean> promotions) {
        if(promotions.contains(PromotionUtils.TWO_OR_MORE_TRAVEL_CARD_HOLDERS)) {
            for(Item product : products) {
                if(product.getProductCode().equals(PromotionUtils.TRAVEL_CARD_HOLDER_CODE)) {
                    product.setPrice(8.50);
                }
            }
        }
        return products;
    }

    public double calculatePricePromotions(double price, ArrayList<Boolean> promotions) {
        if(promotions.contains(PromotionUtils.TEN_PERCENT_OFF)) {
            if(price > 60) {
                double d = price * 0.1;
                price += -d;
            }
        }
        return price;
    }

    public ArrayList<Boolean> getPromotions() {
        return promotions;
    }

}
