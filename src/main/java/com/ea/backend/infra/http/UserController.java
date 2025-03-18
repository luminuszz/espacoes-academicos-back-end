package com.ea.backend.infra.http;
import com.ea.backend.domain.reservation.application.dto.CreateUserDto;
import com.ea.backend.domain.reservation.application.services.UserService;
import com.ea.backend.domain.reservation.enterprise.entity.User;
import com.ea.backend.infra.security.UserAuthenticated;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


@RestController()
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUserDto dto,  HttpServletRequest request) {
            try {
                this.userService.create(dto);

                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }


    }
}
