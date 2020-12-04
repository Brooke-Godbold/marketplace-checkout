package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception Response Status for Product which does not exist.
 * @author Brooke Godbold
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product Code Not Found")
public class ProductCodeNotFoundException extends RuntimeException {
}
