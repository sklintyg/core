package se.inera.intyg.certificateservice.application.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.certificateservice.application.infrastructure.persistence.InMemoryCertificateModelRepository;
import se.inera.intyg.certificateservice.repository.CertificateModelRepository;

@Configuration
public class AppConfig {

  @Bean
  public CertificateModelRepository certificateModelRepository() {
    return new InMemoryCertificateModelRepository();
  }
}
