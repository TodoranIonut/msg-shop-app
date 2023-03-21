package ro.msg.learning.shop.exception.type;

import ro.msg.learning.shop.exception.ShopAppException;

public class ForbiddenException extends ShopAppException {

    public ForbiddenException(String message) {
        super(message);
    }
}
