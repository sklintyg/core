package se.inera.intyg.cts.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.testutil.CertificateTestDataBuilder.certificateEntities;
import static se.inera.intyg.cts.testutil.CertificateTestDataBuilder.certificates;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryCertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class JpaCertificateRepositoryTest {

  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private InMemoryCertificateEntityRepository inMemoryCertificateEntityRepository;
  private JpaCertificateRepository jpaCertificateRepository;
  private Termination termination;
  private TerminationEntity terminationEntity;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    inMemoryCertificateEntityRepository = new InMemoryCertificateEntityRepository();
    jpaCertificateRepository = new JpaCertificateRepository(
        inMemoryCertificateEntityRepository,
        inMemoryTerminationEntityRepository);
    termination = defaultTermination();
    terminationEntity = defaultTerminationEntity();
  }

  @Test
  void shallStoreCertificatesForExistingTermination() {
    inMemoryTerminationEntityRepository.save(terminationEntity);

    jpaCertificateRepository.store(termination, certificates(3, 0));

    assertEquals(3, inMemoryCertificateEntityRepository.count());
  }

  @Test
  void shallReturnCertificatesForExistingTermination() {
    inMemoryTerminationEntityRepository.save(terminationEntity);
    inMemoryCertificateEntityRepository.saveAll(certificateEntities(terminationEntity, 3, 0));

    assertEquals(3, jpaCertificateRepository.get(termination).size());
  }

  @Test
  void shallReturnEmptyForForMissingTermination() {
    assertEquals(0, jpaCertificateRepository.get(termination).size());
  }

  @Test
  void shallRemoveCertificatesForExistingTermination() {
    inMemoryTerminationEntityRepository.save(terminationEntity);
    inMemoryCertificateEntityRepository.saveAll(certificateEntities(terminationEntity, 3, 0));

    jpaCertificateRepository.remove(termination);

    assertEquals(0, jpaCertificateRepository.get(termination).size());
  }
}