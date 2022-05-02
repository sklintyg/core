package se.inera.intyg.cts.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.cts.domain.repository.CertificateBatchRepository;
import se.inera.intyg.cts.domain.repository.CertificateRepository;
import se.inera.intyg.cts.domain.repository.CertificateTextRepository;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.domain.service.CreatePackage;
import se.inera.intyg.cts.domain.service.ExportPackage;
import se.inera.intyg.cts.domain.service.UploadPackage;

@Configuration
public class AppConfig {

  @Bean
  public CollectExportContent collectExportContent(TerminationRepository terminationRepository,
      CertificateBatchRepository certificationBatchRepository,
      CertificateRepository certificationRepository,
      CertificateTextRepository certificateTextRepository) {
    return new CollectExportContent(terminationRepository, certificationBatchRepository,
        certificationRepository, certificateTextRepository);
  }

  @Bean
  public ExportPackage exportPackage(CreatePackage createPackage,
      UploadPackage uploadPackage,
      TerminationRepository terminationRepository) {
    return new ExportPackage(createPackage, uploadPackage, terminationRepository);
  }
}
