package checkout;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import utils.PromotionUtils;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionServiceTest {

    PromotionService promotionService = new PromotionService();

    @Test
    void calculateItemPromotions() {
    }

    @Test
    void calculatePricePromotions() {
        ArrayList<Boolean> promotions = new ArrayList<Boolean>(){{
            add(PromotionUtils.TEN_PERCENT_OFF);
        }};

        assertThat(promotionService.calculatePricePromotions(60, promotions)).isEqualTo(60);
        assertThat(promotionService.calculatePricePromotions(60.1, promotions)).isEqualTo(54.09);
        assertThat(promotionService.calculatePricePromotions(10000, promotions)).isEqualTo(9000);
        assertThat(promotionService.calculatePricePromotions(1, promotions)).isEqualTo(1);
        assertThat(promotionService.calculatePricePromotions(0, promotions)).isEqualTo(0);
    }
}