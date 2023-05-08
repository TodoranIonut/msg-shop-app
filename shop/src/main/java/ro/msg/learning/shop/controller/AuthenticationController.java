package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.controller.dto.AuthenticationRequestDTO;
import ro.msg.learning.shop.controller.dto.AuthenticationResponseDTO;
import ro.msg.learning.shop.service.auth.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody AuthenticationRequestDTO authRequest){
        AuthenticationResponseDTO response = authenticationService.authenticate(authRequest);
        return ResponseEntity.ok().body(response);
    }
}
