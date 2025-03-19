package com.ea.backend.infra.http;

import com.ea.backend.domain.user.application.UserService;
import com.ea.backend.domain.user.application.dto.CreateUserDto;
import com.ea.backend.domain.user.application.dto.MakeLoginDto;
import com.ea.backend.infra.http.model.LoginResponseEntity;
import com.ea.backend.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private  final UserService userService;


    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }


    @PostMapping("/sign-in")
    public ResponseEntity<String> login(@RequestBody @Valid MakeLoginDto dto) {
        try {
            var authentication = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            var results = authenticationManager.authenticate(authentication);
            var token = tokenService.generateToken(results);

            return LoginResponseEntity.build(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid CreateUserDto dto) {
        this.userService.createTeacher(dto);

        return ResponseEntity.ok("User created");
    }
}