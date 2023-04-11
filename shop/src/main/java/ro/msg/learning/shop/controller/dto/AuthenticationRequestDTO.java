package ro.msg.learning.shop.controller.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

    private String username;
    private String password;
}
