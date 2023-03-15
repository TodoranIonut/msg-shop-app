package ro.msg.learning.shop.exception;

public class ShopAppException extends Exception {

    public ShopAppException(String message) {
        super(message);
    }

    public ShopAppException(String message, Throwable cause) {
        super(message, cause);
    }
}