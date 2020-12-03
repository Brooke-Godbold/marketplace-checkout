package integration;

import model.Product;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import utils.RestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCheckoutTotalTest extends BaseIntegrationTest {

    @Test
    public void getTotalWhenTwoTravelCardHolders() {
        Product product = new Product();
        product.setProductCode(001);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(003);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £36.95");
    }

    @Test
    public void getTotalWhenTenPercentOff() {
        Product product = new Product();
        product.setProductCode(003);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(002);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £76.41");
    }

    @Test
    public void getTotalWhenTenPercentOffAndTwoTravelCardHolders() {
        Product product = new Product();
        product.setProductCode(001);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(002);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(003);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £73.76");
    }

    @Test
    public void getTotalWhenNoPromotions() {
        Product product = new Product();
        product.setProductCode(001);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(002);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £54.25");
    }
}
