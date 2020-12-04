package controller;

import checkout.CheckoutService;
import exception.ProductCodeNotFoundException;
import model.Item;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * API Controller for the Checkout System.
 * @author Brooke Godbold
 */
@RestController
public class CheckoutController {

    @Autowired
    CheckoutService checkoutService;

    /**
     * Returns a List of all of Items currently in Inventory.
     *
     * @return  List of Items available in the Inventory
     */
    @GetMapping("/inventory")
    public ArrayList<Item> getInventory() {
        return checkoutService.getInventory();
    }

    /**
     * Receives Product Code, looks up item in Inventory and adds to Basket if it exists.
     *
     * @param   product The Product Code, provided in JSON Request
     * @return          String 'success' if the item was found in Inventory and added to Basked
     */
    @PostMapping(path = "/scan", consumes = "application/json", produces = "text/plain")
    public String scan(@RequestBody Product product) {
        if(checkoutService.scan(product.getProductCode())) {
            return "success";
        } else {
            throw new ProductCodeNotFoundException();
        }
    }

    /**
     * Returns a List of all Items currently in the Basket
     *
     * @return  List of Items currently in the Basket
     */
    @GetMapping("/basket")
    public ArrayList<Item> getBasket() {
        return checkoutService.getBasketProducts();
    }

    /**
     * Calculates the Total Price of all Items in Basket with Promotions.
     * This is returned as a String format. Also clears out the Basket after calculation.
     *
     * @return  String with the Total Price of all Items in the Basket
     */
    @GetMapping(value = "/checkout", produces = "text/plain")
    public String getTotal() {
        Double total = checkoutService.total();
        return "Total Price: £" + String.format("%.2f", total);
    }
}
