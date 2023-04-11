package ro.msg.learning.shop.exception.date;

import ro.msg.learning.shop.exception.type.BadRequestException;

public class IllegalDateFormatExceptions extends BadRequestException {

    public IllegalDateFormatExceptions(){
        super("Date format is invalid");
    }
}
