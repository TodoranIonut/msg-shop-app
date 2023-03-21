package ro.msg.learning.shop.exception.type;

import ro.msg.learning.shop.exception.ShopAppException;

public class BadRequestException extends ShopAppException {

    public BadRequestException(String message) {
        super(message);
    }
}
