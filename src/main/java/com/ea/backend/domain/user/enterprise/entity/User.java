package com.ea.backend.domain.user.enterprise.entity;

import com.ea.backend.domain.school.enterprise.entity.SchoolUnit;
import com.ea.backend.shared.DomainEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
public class User extends DomainEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    private  String email;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "TEXT")
    private String passwordHash;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "TEXT")
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = true)
    private SchoolUnit schoolUnit;


}
