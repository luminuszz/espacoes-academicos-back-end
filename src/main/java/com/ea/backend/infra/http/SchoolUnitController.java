package com.ea.backend.infra.http;

import com.ea.backend.domain.school.application.SchoolUnitService;
import com.ea.backend.domain.school.application.dto.CreateSchoolUnitDto;
import com.ea.backend.domain.school.enterprise.entity.SchoolUnit;
import com.ea.backend.infra.http.model.ListResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/school-units")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class SchoolUnitController {

    private final SchoolUnitService service;

    public SchoolUnitController(SchoolUnitService service) {
        this.service = service;
    }


    @PostMapping
    public SchoolUnit create(@RequestBody @Valid CreateSchoolUnitDto dto) {
        return service.create(dto);
    }


    @GetMapping("/{schoolUnitId}/teachers")
    public ResponseEntity<?> getTeachersBySchoolUnit(
            @Valid
            @PathVariable String schoolUnitId) {

        return ListResponse.build(this.service.getTeachersBySchoolUnit(UUID.fromString(schoolUnitId)));
    }


    @GetMapping
    public List<SchoolUnit> listAll() {
        return service.findAll();
    }


}
