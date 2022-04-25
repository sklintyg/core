package se.inera.intyg.cts.infrastructure.persistence;

import static se.inera.intyg.cts.infrastructure.persistence.entity.CertificateTextEntityMapper.toEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import se.inera.intyg.cts.domain.model.CertificateText;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.CertificateTextRepository;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateTextEntityMapper;
import se.inera.intyg.cts.infrastructure.persistence.repository.CertificateTextEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.TerminationEntityRepository;

@Repository
public class JpaCertificateTextRepository implements CertificateTextRepository {

  private final CertificateTextEntityRepository certificateTextEntityRepository;
  private final TerminationEntityRepository terminationEntityRepository;

  public JpaCertificateTextRepository(
      CertificateTextEntityRepository certificateTextEntityRepository,
      TerminationEntityRepository terminationEntityRepository) {
    this.certificateTextEntityRepository = certificateTextEntityRepository;
    this.terminationEntityRepository = terminationEntityRepository;
  }

  @Override
  public void store(Termination termination, List<CertificateText> certificateTexts) {
    final var terminationEntity = terminationEntityRepository
        .findByTerminationId(termination.terminationId().id())
        .orElseThrow();

    certificateTextEntityRepository.saveAll(
        certificateTexts.stream()
            .map(certificateText -> toEntity(certificateText, terminationEntity))
            .collect(Collectors.toList())
    );
  }

  @Override
  public List<CertificateText> get(Termination termination) {
    final var terminationEntity = terminationEntityRepository
        .findByTerminationId(termination.terminationId().id());

    if (terminationEntity.isEmpty()) {
      return Collections.emptyList();
    }

    return certificateTextEntityRepository.findAllByTermination(terminationEntity.get()).stream()
        .map(CertificateTextEntityMapper::toDomain)
        .collect(Collectors.toList());
  }
}
