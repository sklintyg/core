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
import se.inera.intyg.cts.domain.service.PasswordGenerator;
import se.inera.intyg.cts.domain.service.SendNotification;
import se.inera.intyg.cts.domain.service.SendPackageNotification;
import se.inera.intyg.cts.domain.service.SendPackagePassword;
import se.inera.intyg.cts.domain.service.SendPassword;
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
      TerminationRepository terminationRepository, PasswordGenerator passwordGenerator) {
    return new ExportPackage(createPackage, uploadPackage, terminationRepository, passwordGenerator);
  }

  @Bean
  public SendPackageNotification sendNotifications(SendNotification sendNotification,
      TerminationRepository terminationRepository) {
    return new SendPackageNotification(sendNotification, terminationRepository);
  }

  @Bean
  public SendPackagePassword sendPackagePassword(SendPassword sendPassword,
      TerminationRepository terminationRepository) {
    return new SendPackagePassword(sendPassword, terminationRepository);
  }
}
