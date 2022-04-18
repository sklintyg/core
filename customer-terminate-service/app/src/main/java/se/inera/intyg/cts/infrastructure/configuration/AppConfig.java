package se.inera.intyg.cts.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.inera.intyg.cts.application.service.ExportService;
import se.inera.intyg.cts.application.service.TerminationService;
import se.inera.intyg.cts.application.task.CollectCertificatesTask;
import se.inera.intyg.cts.domain.repository.CertificateBatchRepository;
import se.inera.intyg.cts.domain.repository.CertificateRepository;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatch;
import se.inera.intyg.cts.infrastructure.integration.IntegrationCertificateBatchRepository;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.GetCertificateBatchFromIntygstjanst;
import se.inera.intyg.cts.infrastructure.persistence.JpaCertificateRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.TerminationEntityRepository;

@Configuration
@EnableScheduling
public class AppConfig {

  @Bean
  public TerminationService terminationService(
      TerminationRepository jpaTerminationRepository) {
    return new TerminationService(jpaTerminationRepository);
  }

  @Bean
  public ExportService exportService(TerminationRepository terminationRepository,
      CollectExportContent collectExportContent) {
    return new ExportService(terminationRepository, collectExportContent);
  }

  @Bean
  public CollectExportContent collectExportContent(TerminationRepository terminationRepository,
      CertificateBatchRepository certificationBatchRepository,
      CertificateRepository certificationRepository) {
    return new CollectExportContent(terminationRepository, certificationBatchRepository,
        certificationRepository);
  }

  @Bean
  public TerminationRepository terminationRepository(
      TerminationEntityRepository terminationEntityRepository) {
    return new JpaTerminationRepository(terminationEntityRepository);
  }

  @Bean
  public CertificateRepository certificateRepository(
      CertificateEntityRepository certificateEntityRepository,
      TerminationEntityRepository terminationEntityRepository) {
    return new JpaCertificateRepository(certificateEntityRepository, terminationEntityRepository);
  }

  @Bean
  public CertificateBatchRepository certificateBatchRepository(
      GetCertificateBatch getCertificateBatch,
      @Value("${certificate.batch.size:30}") int certificateBatchSize) {
    return new IntegrationCertificateBatchRepository(getCertificateBatch, certificateBatchSize);
  }

  @Bean
  public GetCertificateBatch certificateBatch(
      @Value("${integration.intygsjanst.baseurl}") String baseUrl,
      @Value("${integration.intygsjanst.certificates.endpoint}") String certificatesEndpoint
  ) {
    return new GetCertificateBatchFromIntygstjanst(baseUrl, certificatesEndpoint);
  }

  @Bean
  public CollectCertificatesTask collectCertificatesTask(ExportService exportService) {
    return new CollectCertificatesTask(exportService);
  }
}
