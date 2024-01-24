package se.inera.intyg.certificateservice.application.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.certificateservice.repository.CertificateTypeInfoRepository;
import se.inera.intyg.certificateservice.service.GetActiveCertificateTypeInfoService;

@Configuration
public class AppConfig {

  @Bean
  public GetActiveCertificateTypeInfoService getCertificateTypeInfoService(
      CertificateTypeInfoRepository certificateTypeInfoRepository) {
    return new GetActiveCertificateTypeInfoService(certificateTypeInfoRepository);
  }
}
