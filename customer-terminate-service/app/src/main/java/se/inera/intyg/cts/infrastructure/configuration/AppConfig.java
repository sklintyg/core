package se.inera.intyg.cts.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import se.inera.intyg.cts.application.service.ExportService;
import se.inera.intyg.cts.application.service.TerminationService;
import se.inera.intyg.cts.application.task.CollectCertificateTextsTask;
import se.inera.intyg.cts.application.task.CollectCertificatesTask;
import se.inera.intyg.cts.application.task.ExportTask;
import se.inera.intyg.cts.domain.repository.CertificateBatchRepository;
import se.inera.intyg.cts.domain.repository.CertificateRepository;
import se.inera.intyg.cts.domain.repository.CertificateTextRepository;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.CollectExportContent;
import se.inera.intyg.cts.domain.service.CreatePackage;
import se.inera.intyg.cts.domain.service.ExportPackage;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatch;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateTexts;
import se.inera.intyg.cts.infrastructure.integration.IntegrationCertificateBatchRepository;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.GetCertificateBatchFromIntygstjanst;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.GetCertificateTextsFromIntygstjanst;
import se.inera.intyg.cts.infrastructure.persistence.JpaCertificateRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaCertificateTextRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.CertificateTextEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.TerminationEntityRepository;
import se.inera.intyg.cts.infrastructure.service.CreateEncryptedZipPackage;

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
      CollectExportContent collectExportContent, ExportPackage exportPackage) {
    return new ExportService(terminationRepository, collectExportContent, exportPackage);
  }

  @Bean
  public CollectExportContent collectExportContent(TerminationRepository terminationRepository,
      CertificateBatchRepository certificationBatchRepository,
      CertificateRepository certificationRepository,
      CertificateTextRepository certificateTextRepository) {
    return new CollectExportContent(terminationRepository, certificationBatchRepository,
        certificationRepository, certificateTextRepository);
  }

  @Bean
  public CreatePackage createPackage(TerminationEntityRepository terminationEntityRepository,
      CertificateEntityRepository certificateEntityRepository,
      CertificateTextEntityRepository certificatTextEntityRepository) {
    return new CreateEncryptedZipPackage(terminationEntityRepository, certificateEntityRepository,
        certificatTextEntityRepository);
  }

  @Bean
  public ExportPackage exportToSjut(CreatePackage createPackage,
      TerminationRepository terminationRepository) {
    return new ExportPackage(createPackage, terminationRepository);
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
  public CertificateTextRepository certificateTextRepository(
      CertificateTextEntityRepository certificateTextEntityRepository,
      TerminationEntityRepository terminationEntityRepository) {
    return new JpaCertificateTextRepository(certificateTextEntityRepository,
        terminationEntityRepository);
  }

  @Bean
  public CertificateBatchRepository certificateBatchRepository(
      GetCertificateBatch getCertificateBatch,
      @Value("${certificate.batch.size:30}") int certificateBatchSize,
      GetCertificateTexts getCertificateTexts) {
    return new IntegrationCertificateBatchRepository(getCertificateBatch, certificateBatchSize,
        getCertificateTexts);
  }

  @Bean
  public GetCertificateBatch getCertificateBatch(WebClient webClient,
      @Value("${integration.intygsjanst.baseurl}") String baseUrl,
      @Value("${integration.intygsjanst.certificates.endpoint}") String certificatesEndpoint
  ) {
    return new GetCertificateBatchFromIntygstjanst(webClient, baseUrl, certificatesEndpoint);
  }

  @Bean
  public GetCertificateTexts getCertificateTexts(WebClient webClient,
      @Value("${integration.intygsjanst.baseurl}") String baseUrl,
      @Value("${integration.intygsjanst.certificate.texts.endpoint}") String certificateTextsEndpoint) {
    return new GetCertificateTextsFromIntygstjanst(webClient, baseUrl, certificateTextsEndpoint);
  }

  @Bean
  public CollectCertificatesTask collectCertificatesTask(ExportService exportService) {
    return new CollectCertificatesTask(exportService);
  }

  @Bean
  public CollectCertificateTextsTask collectCertificateTextsTask(ExportService exportService) {
    return new CollectCertificateTextsTask(exportService);
  }

  @Bean
  public ExportTask exportTask(ExportService exportService) {
    return new ExportTask(exportService);
  }

  @Bean
  public WebClient webClient() {
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();
    return WebClient.builder()
        .exchangeStrategies(strategies)
        .build();
  }
}
