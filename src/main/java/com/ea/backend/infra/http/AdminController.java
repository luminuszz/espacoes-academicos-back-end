package com.ea.backend.infra.http;

import com.ea.backend.domain.reservation.application.services.ReservationService;
import com.ea.backend.domain.space.application.dto.ChangeSpaceStatusDto;
import com.ea.backend.domain.space.application.dto.CreateAcademicSpaceDto;
import com.ea.backend.domain.space.application.service.AcademicSpaceService;
import com.ea.backend.domain.user.application.MetricService;
import com.ea.backend.domain.user.application.UserService;
import com.ea.backend.domain.user.application.dto.CreateUserDto;
import com.ea.backend.infra.http.model.PaginatedResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AcademicSpaceService academicSpaceService;
    private  final ReservationService reservationService;
  private final MetricService metricService;

  public AdminController(
      UserService userService,
      AcademicSpaceService academicSpaceService,
      ReservationService reservationService,
      MetricService metricService) {
        this.userService = userService;
        this.academicSpaceService = academicSpaceService;
        this.reservationService = reservationService;
    this.metricService = metricService;
    }

  @PostMapping("/users")
  public ResponseEntity<?> createAdminUser(@RequestBody @Valid CreateUserDto dto) {
            try {
                this.userService.createAdmin(dto);

               return ResponseEntity.ok().body("User created successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }

  @GetMapping("/users")
  public ResponseEntity<?> fetchTeachersPaginated(
      @Valid @RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
      return PaginatedResponse.build(this.userService.fetchUsersPaginated(page - 1, pageSize));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/spaces")
    public ResponseEntity<?> getSpacesPaginated(
            @Valid
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize
    ) {

        try {
      return PaginatedResponse.build(
          this.academicSpaceService.fetchSpacesPaginated(page - 1, pageSize));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/spaces")
    public ResponseEntity<?> createSpace(@RequestBody @Valid CreateAcademicSpaceDto dto) {
        try {
            this.academicSpaceService.createSpace(dto);

            return ResponseEntity.ok().body("Space created successfully");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PatchMapping("/spaces/{spaceId}/status")
    public ResponseEntity<?> changeSpaceStatus(@PathVariable String spaceId, @RequestBody @Valid ChangeSpaceStatusDto requestBody) {

       try {
           requestBody.setSpaceId(spaceId);

           this.academicSpaceService.changeSpaceStatus(requestBody.getSpaceId(), requestBody.getStatus());

           return ResponseEntity.ok().body("Space status changed successfully");
       }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
    }


    @PatchMapping("/reservations/{reservationId}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable @Valid String reservationId) {

        this.reservationService.cancelReservation(UUID.fromString(reservationId));

        return ResponseEntity.ok().body("Reservation cancelled successfully");

    }

  @GetMapping("/metrics/count")
  public ResponseEntity<?> getMetrics() {
    return ResponseEntity.ok().body(this.metricService.getCounts());
  }

  @GetMapping("/metrics/reservations-by-day-of-week")
  public ResponseEntity<?> countReservationsByDayOfWeek() {
    return ResponseEntity.ok().body(this.metricService.countReservationsByDayOfWeek());
  }

  @GetMapping("/metrics/reservations-by-academic-space-last-7-days")
  public ResponseEntity<?> countReservationsByAcademicSpaceLast7Days() {
    return ResponseEntity.ok().body(this.metricService.countReservationsByAcademicSpaceLast7Days());
  }
}
