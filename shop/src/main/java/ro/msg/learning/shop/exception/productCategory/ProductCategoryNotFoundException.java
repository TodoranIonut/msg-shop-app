package ro.msg.learning.shop.exception.productCategory;

import ro.msg.learning.shop.exception.type.NotFoundException;

public class ProductCategoryNotFoundException extends NotFoundException {

    public ProductCategoryNotFoundException(){
        super("product category NOT found");
    }
}
