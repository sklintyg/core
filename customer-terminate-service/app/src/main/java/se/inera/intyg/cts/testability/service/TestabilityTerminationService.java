package se.inera.intyg.cts.testability.service;

import static se.inera.intyg.cts.testability.dto.TestabilityTerminationDTOMapper.toEntity;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.cts.infrastructure.persistence.entity.ExportEmbeddable;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;
import se.inera.intyg.cts.infrastructure.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.TerminationEntityRepository;
import se.inera.intyg.cts.testability.dto.TestabilityTerminationDTO;

@Service
public class TestabilityTerminationService {

  private final TerminationEntityRepository terminationEntityRepository;
  private final CertificateEntityRepository certificateEntityRepository;

  public TestabilityTerminationService(TerminationEntityRepository terminationEntityRepository,
      CertificateEntityRepository certificateEntityRepository) {
    this.terminationEntityRepository = terminationEntityRepository;
    this.certificateEntityRepository = certificateEntityRepository;
  }

  @Transactional
  public void createTermination(TestabilityTerminationDTO testabilityTerminationDTO) {
    terminationEntityRepository.save(toEntity(testabilityTerminationDTO));
  }

  @Transactional
  public void deleteTermination(UUID terminationId) {
    final var terminationEntity = terminationEntityRepository.findByTerminationId(terminationId);
    terminationEntity.ifPresent(this::deleteTermination);
  }

  public ExportEmbeddable getExport(UUID terminationId) {
    final var terminationEntity = terminationEntityRepository.findByTerminationId(terminationId);
    return terminationEntity.orElseThrow().getExport();
  }

  public int getCertificatesCount(UUID terminationId) {
    return getExport(terminationId).getTotal();
  }

  private void deleteTermination(TerminationEntity terminationEntity) {
    final var certificateEntityList = certificateEntityRepository.findAllByTermination(
        terminationEntity);
    certificateEntityList.forEach(certificateEntityRepository::delete);
    terminationEntityRepository.delete(terminationEntity);
  }
}
