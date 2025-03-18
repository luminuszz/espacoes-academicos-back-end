package com.ea.backend.infra.http;
import com.ea.backend.domain.reservation.application.dto.CreateUserDto;
import com.ea.backend.domain.reservation.application.services.UserService;
import com.ea.backend.domain.space.application.AcademicSpaceService;
import com.ea.backend.domain.space.application.dto.CreateAcademicSpaceDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


@RestController()
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AcademicSpaceService academicSpaceService;

    public AdminController(UserService userService, AcademicSpaceService academicSpaceService) {
        this.userService = userService;
        this.academicSpaceService = academicSpaceService;

    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Valid CreateUserDto dto,  HttpServletRequest request) {
            try {
                this.userService.createAdmin(dto);

               return ResponseEntity.ok().body("User created successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }


    @PostMapping("/spaces")
    public ResponseEntity createSpace(@RequestBody @Valid CreateAcademicSpaceDto dto) {
        this.academicSpaceService.createSpace(dto);

        return ResponseEntity.ok().body("Space created successfully");
    }
}
