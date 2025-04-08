package com.ea.backend.domain.user.enterprise.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import com.ea.backend.shared.DomainEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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


    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "TEXT")
    private UserRole role;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String escola;

}
