package ro.msg.learning.shop.exception.supplier;

import ro.msg.learning.shop.exception.type.NotFoundException;

public class SupplierNotFoundException extends NotFoundException {

    public SupplierNotFoundException(){
        super("supplier NOT found");
    }
}
