package integration;

import model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import utils.RestUtils;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ScanProductTest extends BaseIntegrationTest {

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void canScanProduct() {
        Product product = new Product();
        product.setProductCode(001);

        ResponseEntity scanResponse = RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        assertThat(scanResponse.getBody()).isEqualTo("success");
    }

    @Test
    public void canHandleInvalidProductScan() {
        Product product = new Product();
        product.setProductCode(0);

        ResponseEntity scanResponse = RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);
        assertThat(scanResponse.getStatusCodeValue()).isEqualTo(404);
    }
}
