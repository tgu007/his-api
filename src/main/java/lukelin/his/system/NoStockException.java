package lukelin.his.system;

import lukelin.common.springboot.exception.ApiValidationException;

public class NoStockException extends ApiValidationException {
    public NoStockException(String message) {
        super(message);
    }
}
