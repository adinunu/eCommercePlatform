package id.org.test.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3090214244788009511L;

	public ConflictException(String message) {
        super(message);
    }
}
