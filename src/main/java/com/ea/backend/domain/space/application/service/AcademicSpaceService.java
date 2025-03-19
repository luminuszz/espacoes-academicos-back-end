package com.ea.backend.domain.space.application.service;

import com.ea.backend.domain.space.application.dto.CreateAcademicSpaceDto;
import com.ea.backend.domain.space.application.repository.AcademicSpaceRepository;
import com.ea.backend.domain.space.enterprise.AcademicSpace;
import com.ea.backend.domain.space.enterprise.SpaceStatus;
import com.ea.backend.shared.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AcademicSpaceService {
    @Autowired
    private AcademicSpaceRepository academicSpaceRepository;

    public void createSpace(CreateAcademicSpaceDto dto) {

        var space = new AcademicSpace();

        var existsSpaceWithName = this.academicSpaceRepository.findAcademicSpaceByAcronym(dto.getAcronym());

        if(existsSpaceWithName.isPresent()) {
            throw new RuntimeException("Space with acronym name already exists");
        }

        space.setRoomName(dto.getName());
        space.setDescription(dto.getDescription());
        space.setCapacity(dto.getCapacity());
        space.setAcronym(dto.getAcronym());
        space.setStatus(SpaceStatus.AVAILABLE);

        this.academicSpaceRepository.save(space);

    }

    public  void changeSpaceStatus(String spaceId, String status) {
        var space = this.academicSpaceRepository.findAcademicSpaceById(UUID.fromString(spaceId))
                .orElseThrow(() -> new DomainException("Space not found"));

        space.setStatus(SpaceStatus.valueOf(status));

        this.academicSpaceRepository.save(space);
    }


    public Page<AcademicSpace> fetchSpacesPaginated(int page, int pageSize) {
        return academicSpaceRepository.findAll(PageRequest.of(page, pageSize));
    }


}
