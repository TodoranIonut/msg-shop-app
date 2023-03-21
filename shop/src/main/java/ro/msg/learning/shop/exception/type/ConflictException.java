package ro.msg.learning.shop.exception.type;

import ro.msg.learning.shop.exception.ShopAppException;

public class ConflictException extends ShopAppException {

    public ConflictException(String message) {
        super(message);
    }
}
