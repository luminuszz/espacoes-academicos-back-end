package com.ea.backend.shared;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;



public class DomainEntity {
    @CreatedDate
    @Column(nullable = false)
    protected LocalDateTime createdDate;


    @LastModifiedDate
    @Column(nullable = false)
    protected LocalDateTime updatedDate;

}
