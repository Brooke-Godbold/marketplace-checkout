package checkout;

import model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("inventory")
public class InventoryService {

    private ArrayList<Item> inventory;

    public InventoryService() {
        inventory = new ArrayList<Item>(){{
            add(new Item(001, "Travel Card Holder", 9.25));
            add(new Item(002, "Personalised cufflinks", 45.00));
            add(new Item(003, "Kids T-shirt", 19.95));
        }};
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
