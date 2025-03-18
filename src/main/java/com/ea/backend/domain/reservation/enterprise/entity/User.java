package com.ea.backend.domain.reservation.enterprise.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    private  String email;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String passwordHash;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "TEXT")
    private UserRole role;

    @OneToMany(targetEntity = Reservation.class)
    private List<Reservation> reservations;


}
