package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.infrastructure.integration.IntegrationCertificateBatchRepository;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.GetCertificateBatchFromIntygstjanst;
import se.inera.intyg.cts.infrastructure.persistence.JpaCertificateRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryCertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class ExportServiceTest {

  private JpaTerminationRepository terminationRepository;
  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private InMemoryCertificateEntityRepository inMemoryCertificateEntityRepository;
  private JpaCertificateRepository certificateRepository;
  private IntegrationCertificateBatchRepository integrationCertificateBatchRepository;
  private ExportService exportService;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    terminationRepository = new JpaTerminationRepository(inMemoryTerminationEntityRepository);
    inMemoryCertificateEntityRepository = new InMemoryCertificateEntityRepository();
    certificateRepository = new JpaCertificateRepository(inMemoryCertificateEntityRepository,
        inMemoryTerminationEntityRepository);
    integrationCertificateBatchRepository = new IntegrationCertificateBatchRepository(
        new GetCertificateBatchFromIntygstjanst(), 10);
    final var collectExportContent = new CollectExportContent(terminationRepository,
        integrationCertificateBatchRepository, certificateRepository);
    exportService = new ExportService(terminationRepository, collectExportContent);
  }

  @Test
  void shallCollectCertificatesForTermination() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity());
    exportService.collectCertificatesToExport();
    assertEquals(10, inMemoryCertificateEntityRepository.count());
  }
}