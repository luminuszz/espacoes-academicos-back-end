package com.ea.backend.domain.reservation.enterprise.entity;
import com.ea.backend.domain.space.enterprise.AcademicSpace;
import com.ea.backend.shared.DomainEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
@Table(name = "reservations")
@Entity(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends DomainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;


    @ManyToOne
    @JoinColumn(name = "academic_space_id", nullable = false)
    private AcademicSpace academicSpace;


    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
