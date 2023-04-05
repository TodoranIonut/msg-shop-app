package ro.msg.learning.shop.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.controller.dto.AuthenticationRequestDTO;
import ro.msg.learning.shop.controller.dto.AuthenticationResponseDTO;
import ro.msg.learning.shop.domain.repository.CustomerRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = customerRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("not found"));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .token(jwtToken).build();
    }
}
