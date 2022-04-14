package se.inera.intyg.cts.testability.dto;

import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public class TestabilityTerminationDTOMapper {

  public static TerminationEntity toEntity(TestabilityTerminationDTO testabilityTerminationDTO) {
    return new TerminationEntity(
        testabilityTerminationDTO.terminationId(),
        testabilityTerminationDTO.created(),
        testabilityTerminationDTO.creatorHSAId(),
        testabilityTerminationDTO.creatorName(),
        testabilityTerminationDTO.hsaId(),
        testabilityTerminationDTO.organizationNumber(),
        testabilityTerminationDTO.personId(),
        testabilityTerminationDTO.phoneNumber(),
        testabilityTerminationDTO.status());
  }
}
