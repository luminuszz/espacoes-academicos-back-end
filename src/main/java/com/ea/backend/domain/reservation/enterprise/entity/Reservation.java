package com.ea.backend.domain.reservation.enterprise.entity;

import com.ea.backend.domain.space.enterprise.AcademicSpace;
import com.ea.backend.domain.user.enterprise.entity.User;
import com.ea.backend.shared.DomainEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


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
    @JsonBackReference()
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private ReservationStatus status;


    public boolean isPending() {
        return this.status.equals(ReservationStatus.PENDING);
    }

    public boolean isCanceled() {
        return this.status.equals(ReservationStatus.CANCELED);
    }

    public boolean isConfirmedByTheUser() {
        return this.status.equals(ReservationStatus.CONFIRMED_BY_THE_USER);
    }

    public boolean isConfirmedVyEnterprise() {
        return this.status.equals(ReservationStatus.CONFIRMED_BY_THE_ENTERPRISE);
    }

    public boolean isConfirmed() {
        return this.isConfirmedByTheUser() || this.isConfirmedVyEnterprise();
    }

}
