package checkout;

import model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Service which handles the Inventory.
 * Can Load and return the Inventory.
 *
 * @author Brooke Godbold
 */
@Component("inventory")
public class InventoryService {

    private final ArrayList<Item> INVENTORY;

    public InventoryService() {
        INVENTORY = new ArrayList<Item>(){{
            add(new Item(1, "Travel Card Holder", 9.25));
            add(new Item(2, "Personalised cufflinks", 45.00));
            add(new Item(3, "Kids T-shirt", 19.95));
        }};
    }

    public ArrayList<Item> getInventory() {
        return INVENTORY;
    }
}
