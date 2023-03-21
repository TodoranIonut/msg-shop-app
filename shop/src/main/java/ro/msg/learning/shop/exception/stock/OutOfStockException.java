package ro.msg.learning.shop.exception.stock;

import ro.msg.learning.shop.exception.type.BadRequestException;

public class OutOfStockException extends BadRequestException {

    public OutOfStockException(){
        super("order demand is to high for actual stock");
    }
}
