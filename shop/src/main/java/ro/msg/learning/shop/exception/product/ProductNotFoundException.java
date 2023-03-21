package ro.msg.learning.shop.exception.product;

import ro.msg.learning.shop.exception.type.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException(Integer productId){
        super(String.format("Product id:%d not found",productId));
    }
}
