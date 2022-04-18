package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.testutil.CertificateTestDataBuilder.certificates;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatchFromMemory;
import se.inera.intyg.cts.infrastructure.integration.IntegrationCertificateBatchRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaCertificateRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryCertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class ExportServiceTest {

  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private InMemoryCertificateEntityRepository inMemoryCertificateEntityRepository;
  private GetCertificateBatchFromMemory getCertificateBatchFromMemory;
  private ExportService exportService;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    final var terminationRepository = new JpaTerminationRepository(
        inMemoryTerminationEntityRepository);
    inMemoryCertificateEntityRepository = new InMemoryCertificateEntityRepository();
    final var certificateRepository = new JpaCertificateRepository(
        inMemoryCertificateEntityRepository,
        inMemoryTerminationEntityRepository);

    getCertificateBatchFromMemory = new GetCertificateBatchFromMemory();
    final var integrationCertificateBatchRepository = new IntegrationCertificateBatchRepository(
        getCertificateBatchFromMemory, 10);

    final var collectExportContent = new CollectExportContent(terminationRepository,
        integrationCertificateBatchRepository, certificateRepository);
    exportService = new ExportService(terminationRepository, collectExportContent);
  }

  @Test
  void shallCollectCertificatesForTermination() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity());
    getCertificateBatchFromMemory.prepare(certificates(10, 0));

    exportService.collectCertificatesToExport();

    assertEquals(10, inMemoryCertificateEntityRepository.count());
  }
}