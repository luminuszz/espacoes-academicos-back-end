package com.ea.backend.infra.http.model;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public class PaginatedResponse {
    public static ResponseEntity build(Page<?> pageEntity) {

    var map =
        Map.of(
            "page",
            pageEntity.getNumber() + 1,
            "pageSize",
            pageEntity.getSize(),
            "totalOfPages",
            pageEntity.getTotalPages(),
            "content",
            pageEntity.get());

        return ResponseEntity.ok().body(map);
    }

}
