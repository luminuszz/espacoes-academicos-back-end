package com.ea.backend.domain.reservation.enterprise.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String  room_name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int capacity;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;


    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;


    @ManyToOne(targetEntity = User.class)
    @JoinColumn()
    private User user;

}
