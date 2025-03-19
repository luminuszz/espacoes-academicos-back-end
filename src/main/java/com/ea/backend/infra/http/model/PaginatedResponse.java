package com.ea.backend.infra.http.model;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public class PaginatedResponse {
    public static ResponseEntity build(Page<?> pageEntity) {

        var map = Map.of("page", pageEntity.getNumber(),
                "pageSize", pageEntity.getSize(),
                "totalOfPages", pageEntity.getTotalPages(),
                "data", pageEntity.get()
        );

        return ResponseEntity.ok().body(map);
    }

}
