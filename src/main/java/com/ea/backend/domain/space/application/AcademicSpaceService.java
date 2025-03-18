package com.ea.backend.domain.space.application;

import com.ea.backend.domain.space.application.dto.CreateAcademicSpaceDto;
import com.ea.backend.domain.space.enterprise.AcademicSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcademicSpaceService {
    @Autowired
    private AcademicSpaceRepository academicSpaceRepository;

    public void createSpace(CreateAcademicSpaceDto dto) {

        var space = new AcademicSpace();

        var existsSpaceWithName = this.academicSpaceRepository.findByRoomName(dto.getName());

        if(existsSpaceWithName.isPresent()) {
            throw new RuntimeException("Space with name already exists");
        }

        space.setRoomName(dto.getName());
        space.setDescription(dto.getDescription());
        space.setCapacity(dto.getCapacity());


        this.academicSpaceRepository.save(space);

    }


}
