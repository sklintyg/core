package se.inera.intyg.certificateservice.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.patient.GetPatientCertificatesDomainService;

@Configuration
public class AppConfig {

  @Bean
  public CreateCertificateDomainService createCertificateDomainService(
      CertificateModelRepository certificateModelRepository,
      CertificateRepository certificateRepository) {
    return new CreateCertificateDomainService(certificateModelRepository, certificateRepository);
  }

  @Bean
  public GetCertificateDomainService getCertificateDomainService(
      CertificateRepository certificateRepository) {
    return new GetCertificateDomainService(certificateRepository);
  }

  @Bean
  public UpdateCertificateDomainService updateCertificateDomainService(
      CertificateRepository certificateRepository) {
    return new UpdateCertificateDomainService(certificateRepository);
  }

  @Bean
  public DeleteCertificateDomainService deleteCertificateDomainService(
      CertificateRepository certificateRepository) {
    return new DeleteCertificateDomainService(certificateRepository);
  }

  @Bean
  public GetPatientCertificatesDomainService getPatientCertificatesDomainService(
      CertificateRepository certificateRepository) {
    return new GetPatientCertificatesDomainService(certificateRepository);
  }
}
