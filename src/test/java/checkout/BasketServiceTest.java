package checkout;

import model.Item;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BasketServiceTest {

    BasketService basketService = new BasketService();

    @Test
    void getBasketProductsDetailsTest() {
        Item testItem = new Item(005, "Test Item", 7.85);
        basketService.addToBasket(testItem);
        assertThat(basketService.getBasketProducts().get(0).getProductCode()).isEqualTo(5);
        assertThat(basketService.getBasketProducts().get(0).getName()).isEqualTo("Test Item");
        assertThat(basketService.getBasketProducts().get(0).getPrice()).isEqualTo(7.85);
    }

    @Test
    void getBasketProductsSizeTest() {
        for(int i=0; i<10; i++) {
            basketService.addToBasket(mock(Item.class));
        }
        assertThat(basketService.getBasketProducts().size()).isEqualTo(10);
    }

    @Test
    void clearBasket() {
        Item mockItem = mock(Item.class);
        basketService.addToBasket(mockItem);
        basketService.clearBasket();
        assertThat(basketService.getBasketProducts().size()).isEqualTo(0);
    }
}