package com.ea.backend.infra.http.model;

import java.util.List;
import org.springframework.http.ResponseEntity;

public class ListResponse {

  public static ResponseEntity<List<?>> build(List<?> objects) {

    return ResponseEntity.ok(objects);
  }
}
