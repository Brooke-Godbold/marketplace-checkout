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

@RestController
public class CheckoutController {

    @Autowired
    CheckoutService checkoutService;

    @GetMapping("/inventory")
    public ArrayList<Item> getInventory() {
        return checkoutService.getInventory();
    }

    @PostMapping(path = "/scan", consumes = "application/json", produces = "text/plain")
    public String scan(@RequestBody Product product) {
        if(checkoutService.scan(product.getProductCode())) {
            return "success";
        } else {
            throw new ProductCodeNotFoundException();
        }
    }

    @GetMapping("/basket")
    public ArrayList<Item> getBasket() {
        return checkoutService.getBasketProducts();
    }

    @GetMapping(value = "/checkout", produces = "text/plain")
    public String getTotal() {
        Double total = checkoutService.total();
        return "Total Price: £" + String.format("%.2f", total);
    }
}
