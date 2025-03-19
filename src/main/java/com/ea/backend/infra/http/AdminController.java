package com.ea.backend.infra.http;

import com.ea.backend.domain.reservation.application.services.ReservationService;
import com.ea.backend.domain.space.application.dto.ChangeSpaceStatusDto;
import com.ea.backend.domain.space.application.dto.CreateAcademicSpaceDto;
import com.ea.backend.domain.space.application.service.AcademicSpaceService;
import com.ea.backend.domain.user.application.UserService;
import com.ea.backend.domain.user.application.dto.CreateUserDto;
import com.ea.backend.infra.http.model.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController()
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AcademicSpaceService academicSpaceService;
    private  final ReservationService reservationService;

    public AdminController(UserService userService, AcademicSpaceService academicSpaceService, ReservationService reservationService) {
        this.userService = userService;
        this.academicSpaceService = academicSpaceService;
        this.reservationService = reservationService;

    }

    @PostMapping("/users")
    public ResponseEntity createAdminUser(@RequestBody @Valid CreateUserDto dto, HttpServletRequest request) {
            try {
                this.userService.createAdmin(dto);

               return ResponseEntity.ok().body("User created successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }


    @GetMapping("/teachers")
    public ResponseEntity<?> fetchTeachersPaginated(
            @Valid
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize
    ) {
        try {
            return PaginatedResponse.build(this.userService.fetchTeachersPaginated(page, pageSize));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/spaces")
    public ResponseEntity getSpacesPaginated(
            @Valid
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize
    ) {

        try {
            return PaginatedResponse.build(this.academicSpaceService.fetchSpacesPaginated(page, pageSize));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/spaces")
    public ResponseEntity createSpace(@RequestBody @Valid CreateAcademicSpaceDto dto) {
        try {
            this.academicSpaceService.createSpace(dto);

            return ResponseEntity.ok().body("Space created successfully");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PatchMapping("/spaces/{spaceId}/status")
    public ResponseEntity changeSpaceStatus(@PathVariable String spaceId , @RequestBody @Valid ChangeSpaceStatusDto requestBody) {

       try {
           requestBody.setSpaceId(spaceId);

           this.academicSpaceService.changeSpaceStatus(requestBody.getSpaceId(), requestBody.getStatus());

           return ResponseEntity.ok().body("Space status changed successfully");
       }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
    }


    @PatchMapping("/reservations/{reservationId}/cancel")
    public ResponseEntity cancelReservation(@PathVariable String reservationId) {

        this.reservationService.cancelReservation(UUID.fromString(reservationId));

        return ResponseEntity.ok().body("Reservation cancelled successfully");

    }


}
