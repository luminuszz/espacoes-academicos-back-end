package com.ea.backend.domain.school.application;

import com.ea.backend.domain.school.application.dto.CreateSchoolUnitDto;
import com.ea.backend.domain.school.application.repository.SchoolUnitRepository;
import com.ea.backend.domain.school.enterprise.entity.SchoolUnit;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SchoolUnitService {

    private final SchoolUnitRepository repository;

    public SchoolUnitService(SchoolUnitRepository repository) {
        this.repository = repository;
    }

    public SchoolUnit create(CreateSchoolUnitDto dto) {
        SchoolUnit unit = new SchoolUnit();
        unit.setName(dto.name());
        unit.setType(dto.type());
        unit.setState(dto.state());
        unit.setCity(dto.city());
        unit.setAddress(dto.address());
        unit.setContactNumber(dto.contactNumber());

        return repository.save(unit);
    }

    public List<SchoolUnit> findAll() {
        return repository.findAll();
    }
}
