package ro.msg.learning.shop.exception.type;

import ro.msg.learning.shop.exception.ShopAppException;

public class NotFoundException extends ShopAppException {

    public NotFoundException(String message) {
        super(message);
    }
}
