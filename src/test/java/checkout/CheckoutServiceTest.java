package checkout;

import model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckoutServiceTest {

    CheckoutService checkoutService = new CheckoutService();

    ArrayList<Item> mockInventory = new ArrayList<Item>(){{
        add(new Item(1, "Travel Card Holder", 9.25));
        add(new Item(2, "Personalised cufflinks", 45.00));
        add(new Item(3, "Kids T-shirt", 19.95));
    }};

    @Test
    void scanValidProductCodeTest() {
        InventoryService mockInventoryService = mock(InventoryService.class);
        when(mockInventoryService.getInventory()).thenReturn(mockInventory);
        checkoutService.inventoryService = mockInventoryService;

        BasketService mockBasketService = mock(BasketService.class);
        Mockito.doNothing().when(mockBasketService).addToBasket(any(Item.class));
        checkoutService.basketService = mockBasketService;

        mockInventoryService.getInventory().forEach(
                item -> assertThat(checkoutService.scan(item.getProductCode())).isTrue()
        );
    }

    @ParameterizedTest(name = "{index} Product Code {0} cannot be scanned")
    @ValueSource(ints = {0, -1, 10000})
    void scanInvalidProductCodeTest(int productCode) {
        InventoryService mockInventoryService = mock(InventoryService.class);
        when(mockInventoryService.getInventory()).thenReturn(mockInventory);
        checkoutService.inventoryService = mockInventoryService;

        assertThat(checkoutService.scan(productCode)).isFalse();
    }

    @Test
    void getBasketProducts() {
        ArrayList<Item> items = new ArrayList<>();
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
        InventoryService mockInventoryService = mock(InventoryService.class);
        when(mockInventoryService.getInventory()).thenReturn(mockInventory);
        checkoutService.inventoryService = mockInventoryService;

        assertThat(checkoutService.getInventory()).isEqualTo(mockInventoryService.getInventory());
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
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(9, "Test Item 1", 15.5));
        items.add(new Item(9, "Test Item 2", 23.4));
        items.add(new Item(3, "Test Item 3", 13.9));

        BasketService mockBasketService = mock(BasketService.class);
        when(mockBasketService.getBasketProducts()).thenReturn(items);
        checkoutService.basketService = mockBasketService;

        assertThat(checkoutService.getTotalPrice()).isEqualTo(52.8);
    }
}