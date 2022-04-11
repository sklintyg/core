package se.inera.intyg.cts.testability.service;

import static se.inera.intyg.cts.testability.dto.TestabilityTerminationDTOMapper.toEntity;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.cts.infrastructure.persistence.repository.TerminationEntityRepository;
import se.inera.intyg.cts.testability.dto.TestabilityTerminationDTO;

@Service
public class TestabilityTerminationService {

  private final TerminationEntityRepository terminationEntityRepository;

  public TestabilityTerminationService(TerminationEntityRepository terminationEntityRepository) {
    this.terminationEntityRepository = terminationEntityRepository;
  }

  @Transactional
  public void createTermination(TestabilityTerminationDTO testabilityTerminationDTO) {
    terminationEntityRepository.save(toEntity(testabilityTerminationDTO));
  }

  @Transactional
  public void deleteTermination(UUID terminationId) {
    final var terminationEntity = terminationEntityRepository.findByTerminationId(terminationId);
    terminationEntity.ifPresent(terminationEntityRepository::delete);
  }
}
