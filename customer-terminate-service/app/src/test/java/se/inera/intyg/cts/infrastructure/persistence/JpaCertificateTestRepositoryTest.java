package se.inera.intyg.cts.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.testutil.CertificateTextTestDataBuilder.certificateTextEntities;
import static se.inera.intyg.cts.testutil.CertificateTextTestDataBuilder.certificateTexts;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryCertificateTextsEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class JpaCertificateTestRepositoryTest {

  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private InMemoryCertificateTextsEntityRepository inMemoryCertificateTextsEntityRepository;
  private JpaCertificateTextRepository jpaCertificateTextRepository;
  private Termination termination;
  private TerminationEntity terminationEntity;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    inMemoryCertificateTextsEntityRepository = new InMemoryCertificateTextsEntityRepository();
    jpaCertificateTextRepository = new JpaCertificateTextRepository(
        inMemoryCertificateTextsEntityRepository,
        inMemoryTerminationEntityRepository);
    termination = defaultTermination();
    terminationEntity = defaultTerminationEntity();
  }

  @Test
  void shallStoreCertificateTextsForExistingTermination() {
    inMemoryTerminationEntityRepository.save(terminationEntity);

    jpaCertificateTextRepository.store(termination, certificateTexts(3));

    assertEquals(3, inMemoryCertificateTextsEntityRepository.count());
  }

  @Test
  void shallReturnCertificateTextsForExistingTermination() {
    inMemoryTerminationEntityRepository.save(terminationEntity);
    inMemoryCertificateTextsEntityRepository.saveAll(certificateTextEntities(terminationEntity, 3));

    assertEquals(3, jpaCertificateTextRepository.get(termination).size());
  }

  @Test
  void shallReturnEmptyForForMissingTermination() {
    assertEquals(0, jpaCertificateTextRepository.get(termination).size());
  }
}