package utils;

import model.Item;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class RestUtils {

    public static final String INVENTORY = "/inventory";
    public static final String BASKET = "/basket";
    public static final String SCAN = "/scan";
    public static final String CHECKOUT = "/checkout";

    public static ResponseEntity getItemListResponse(TestRestTemplate restTemplate, int port, String api) {
        return restTemplate.exchange(
                "http://localhost:" + port + api,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Item>>() {}
        );
    }

    public static ResponseEntity getStringResponse(TestRestTemplate restTemplate, int port, String api) {
        return restTemplate.exchange(
                "http://localhost:" + port + api,
                HttpMethod.GET,
                null,
                String.class
        );
    }

    public static ResponseEntity postReturnString(TestRestTemplate restTemplate, int port, String api, Object request) {
        return restTemplate.exchange(
                "http://localhost:" + port + api,
                HttpMethod.POST,
                new HttpEntity<>(request),
                String.class
        );
    }
}
