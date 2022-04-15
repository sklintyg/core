package se.inera.intyg.cts.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntityMapper.toEntity;
import static se.inera.intyg.cts.testutil.CertificateTestDataBuilder.defaultCertificate;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryCertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class JpaCertificateRepositoryTest {

  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private InMemoryCertificateEntityRepository inMemoryCertificateEntityRepository;
  private JpaCertificateRepository jpaCertificateRepository;
  private Termination termination;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    inMemoryCertificateEntityRepository = new InMemoryCertificateEntityRepository();
    jpaCertificateRepository = new JpaCertificateRepository(
        inMemoryCertificateEntityRepository,
        inMemoryTerminationEntityRepository);
    termination = defaultTermination();
  }

  @Test
  void shallStoreCertificatesForExistingTermination() {
    inMemoryTerminationEntityRepository.save(toEntity(termination));

    jpaCertificateRepository.store(termination,
        Arrays.asList(defaultCertificate(), defaultCertificate(), defaultCertificate())
    );

    assertEquals(3, inMemoryCertificateEntityRepository.count());
  }
}