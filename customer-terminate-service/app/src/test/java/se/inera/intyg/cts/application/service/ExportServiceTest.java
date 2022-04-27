package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.testutil.CertificateTestDataBuilder.certificates;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.domain.service.ExportPackage;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatchFromMemory;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateTextsFromMemory;
import se.inera.intyg.cts.infrastructure.integration.IntegrationCertificateBatchRepository;
import se.inera.intyg.cts.infrastructure.integration.UploadPackageToMemory;
import se.inera.intyg.cts.infrastructure.persistence.JpaCertificateRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaCertificateTextRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryCertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryCertificateTextsEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;
import se.inera.intyg.cts.infrastructure.service.CreateEncryptedZipPackage;

class ExportServiceTest {

  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private InMemoryCertificateEntityRepository inMemoryCertificateEntityRepository;
  private InMemoryCertificateTextsEntityRepository inMemoryCertificateTextsEntityRepository;
  private GetCertificateBatchFromMemory getCertificateBatchFromMemory;
  private GetCertificateTextsFromMemory getCertificateTextsFromMemory;
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
    inMemoryCertificateTextsEntityRepository = new InMemoryCertificateTextsEntityRepository();
    final var certificateTextRepository = new JpaCertificateTextRepository(
        inMemoryCertificateTextsEntityRepository,
        inMemoryTerminationEntityRepository);

    getCertificateBatchFromMemory = new GetCertificateBatchFromMemory();
    getCertificateTextsFromMemory = new GetCertificateTextsFromMemory();
    final var integrationCertificateBatchRepository = new IntegrationCertificateBatchRepository(
        getCertificateBatchFromMemory, 10, getCertificateTextsFromMemory);

    final var collectExportContent = new CollectExportContent(terminationRepository,
        integrationCertificateBatchRepository, certificateRepository, certificateTextRepository);
    final var createPackage = new CreateEncryptedZipPackage(inMemoryTerminationEntityRepository,
        inMemoryCertificateEntityRepository, inMemoryCertificateTextsEntityRepository, "./");
    final var uploadPackage = new UploadPackageToMemory();
    final var exportPackage = new ExportPackage(createPackage, uploadPackage,
        terminationRepository);
    exportService = new ExportService(terminationRepository, collectExportContent, exportPackage);
  }

  @Test
  void shallCollectCertificatesForTermination() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity());
    getCertificateBatchFromMemory.prepare(certificates(10, 0));

    exportService.collectCertificatesToExport();

    assertEquals(10, inMemoryCertificateEntityRepository.count());
  }
}