package com.ea.backend.infra.http;

import com.ea.backend.domain.school.application.SchoolUnitService;
import com.ea.backend.domain.school.application.dto.CreateSchoolUnitDto;
import com.ea.backend.domain.school.enterprise.entity.SchoolUnit;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school-units")
public class SchoolUnitController {

    private final SchoolUnitService service;

    public SchoolUnitController(SchoolUnitService service) {
        this.service = service;
    }

    @PostMapping
    public SchoolUnit create(@RequestBody @Valid CreateSchoolUnitDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<SchoolUnit> listAll() {
        return service.findAll();
    }
}
