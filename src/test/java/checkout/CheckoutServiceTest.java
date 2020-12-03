package checkout;

import model.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckoutServiceTest {

    CheckoutService checkoutService = new CheckoutService();

    @Test
    void scanValidProductCodeTest() {
        InventoryService inventoryService = new InventoryService();
        checkoutService.inventoryService = inventoryService;

        BasketService mockBasketService = mock(BasketService.class);
        Mockito.doNothing().when(mockBasketService).addToBasket(any(Item.class));
        checkoutService.basketService = mockBasketService;

        inventoryService.getInventory().forEach(item -> {
            assertThat(checkoutService.scan(item.getProductCode())).isTrue();
        });
    }

    @Test
    void scanInvalidProductCodeTest() {
        checkoutService.inventoryService = new InventoryService();

        assertThat(checkoutService.scan(0)).isFalse();
        assertThat(checkoutService.scan(-1)).isFalse();
        assertThat(checkoutService.scan(10000)).isFalse();
    }

    @Test
    void getBasketProducts() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item(9, "Test Item 1", 15.5));
        items.add(new Item(9, "Test Item 2", 23.4));
        items.add(new Item(3, "Test Item 3", 13.9));

        BasketService basketService = mock(BasketService.class);
        when(basketService.getBasketProducts()).thenReturn(items);
        checkoutService.basketService = basketService;

        assertThat(checkoutService.getBasketProducts()).isEqualTo(basketService.getBasketProducts());
    }

    @Test
    void getInventory() {
        InventoryService inventoryService = new InventoryService();
        checkoutService.inventoryService = inventoryService;
        assertThat(checkoutService.getInventory()).isEqualTo(inventoryService.getInventory());
    }

    @Test
    void total() {
        CheckoutService checkoutSpy = Mockito.spy(checkoutService);

        ArrayList<Item> mockBasketProducts = new ArrayList<>();
        ArrayList<Boolean> mockPromotions = new ArrayList<>();
        ArrayList<Item> mockItemPromotions = new ArrayList<>();

        BasketService mockBasketService = mock(BasketService.class);
        when(mockBasketService.getBasketProducts()).thenReturn(mockBasketProducts);
        Mockito.doNothing().when(mockBasketService).setBasketProducts(mockItemPromotions);
        Mockito.doNothing().when(mockBasketService).clearBasket();
        checkoutSpy.basketService = mockBasketService;

        Mockito.doReturn((double) 20).when(checkoutSpy).getTotalPrice();

        PromotionService mockPromotionService = mock(PromotionService.class);
        when(mockPromotionService.getPromotions()).thenReturn(mockPromotions);
        when(mockPromotionService.calculateItemPromotions(mockBasketProducts, mockPromotions)).thenReturn(mockItemPromotions);
        when(mockPromotionService.calculatePricePromotions(anyDouble(), eq(mockPromotions))).thenReturn((double) 55);
        checkoutSpy.promotionService = mockPromotionService;

        assertThat(checkoutSpy.total()).isEqualTo(55);
    }

    @Test
    void getTotalPrice() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item(9, "Test Item 1", 15.5));
        items.add(new Item(9, "Test Item 2", 23.4));
        items.add(new Item(3, "Test Item 3", 13.9));

        BasketService mockBasketService = mock(BasketService.class);
        when(mockBasketService.getBasketProducts()).thenReturn(items);
        checkoutService.basketService = mockBasketService;

        assertThat(checkoutService.getTotalPrice()).isEqualTo(52.8);
    }
}