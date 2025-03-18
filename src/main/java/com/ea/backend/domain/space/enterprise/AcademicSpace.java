package com.ea.backend.domain.space.enterprise;


import com.ea.backend.shared.DomainEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "spaces")
@Table(name = "spaces")
public class AcademicSpace extends DomainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    protected UUID id;

    @Column(nullable = false, unique = true, name = "room_name")
    private String  roomName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int capacity;

}
