package ro.msg.learning.shop.exception.date;

import ro.msg.learning.shop.exception.type.BadRequestException;

public class IllegalDateStringExceptions extends BadRequestException {

    public IllegalDateStringExceptions(){
        super("Date string does NOT correspond with date format");
    }
}
