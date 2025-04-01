package com.ea.backend.domain.space.application.service;

import com.ea.backend.domain.space.application.dto.CreateAcademicSpaceDto;
import com.ea.backend.domain.space.application.repository.AcademicSpaceRepository;
import com.ea.backend.domain.space.enterprise.AcademicSpace;
import com.ea.backend.domain.space.enterprise.SpaceStatus;
import com.ea.backend.shared.DomainException;
import com.ea.backend.shared.DomainExceptionCode;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AcademicSpaceService {
    @Autowired
    private AcademicSpaceRepository academicSpaceRepository;

    public void createSpace(CreateAcademicSpaceDto dto) {

        var space = new AcademicSpace();

        var existsSpaceWithName = this.academicSpaceRepository.findAcademicSpaceByAcronym(dto.getAcronym());

    existsSpaceWithName.ifPresent(
        (spaceFound) -> {
          throw new DomainException(
              "Space with this acronym already exists", DomainExceptionCode.DUPLICATE_FOUND);
        });

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

  public Page<AcademicSpace> fetchSpacesPaginated(
      int page, int pageSize, String filterField, String filterValue) {

    PageRequest pageRequest = PageRequest.of(page, pageSize);

    if ("roomName".equalsIgnoreCase(filterField)) {
      return academicSpaceRepository.findByRoomNameContainingIgnoreCaseOrderByCreatedAtDesc(
          filterValue, pageRequest);
    } else if ("acronym".equalsIgnoreCase(filterField)) {
      return academicSpaceRepository.findByAcronymContainingIgnoreCaseOrderByCreatedAtDesc(
          filterValue, pageRequest);
    } else {
      return academicSpaceRepository.findAllByOrderByCreatedAtDesc(pageRequest);
    }
  }

  public List<AcademicSpace> fetchAllAvailableAcademicSpaces() {
    return this.academicSpaceRepository.findAllByStatus(SpaceStatus.AVAILABLE);
  }
}
