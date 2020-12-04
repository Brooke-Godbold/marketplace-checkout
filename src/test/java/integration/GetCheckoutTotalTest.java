package integration;

import model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.ResponseEntity;
import utils.RestUtils;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCheckoutTotalTest extends BaseIntegrationTest {

    @Test
    public void getTotalWhenTwoTravelCardHolders() {
        Product product = new Product();
        product.setProductCode(1);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(3);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £36.95");
    }

    @Test
    public void getTotalWhenTenPercentOff() {
        Product product = new Product();
        product.setProductCode(3);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(2);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £76.41");
    }

    @Test
    public void getTotalWhenTenPercentOffAndTwoTravelCardHolders() {
        Product product = new Product();
        product.setProductCode(1);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(2);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(3);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £73.76");
    }

    @Test
    public void getTotalWhenNoPromotions() {
        Product product = new Product();
        product.setProductCode(1);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(2);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £54.25");
    }

    private static Stream<Arguments> getIntegrationTestArgs() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3}, "66.78"),
                Arguments.of(new int[]{1, 3, 1}, "36.95"),
                Arguments.of(new int[]{1, 2, 1, 3}, "73.76")
        );
    }

    @ParameterizedTest(name = "{index} Products {0} will return a Price of {1}")
    @MethodSource("getIntegrationTestArgs")
    public void checkoutIntegrationTest(int[] productCodes, String expectedPrice) {
        Product product = new Product();

        for(int productCode : productCodes) {
            product.setProductCode(productCode);
            RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        }

        ResponseEntity responseEntity = RestUtils.getStringResponse(restTemplate, port, RestUtils.CHECKOUT);
        assertThat(responseEntity.getBody()).isEqualTo("Total Price: £" + expectedPrice);
    }
}
