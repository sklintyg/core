package se.inera.intyg.cts.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.cts.application.service.TerminationService;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.TerminationEntityRepository;

@Configuration
public class AppConfig {

  @Bean
  public TerminationService createTerminationService(
      TerminationEntityRepository terminationEntityRepository) {
    return new TerminationService(jpaTerminationRepository(terminationEntityRepository));
  }

  @Bean
  public JpaTerminationRepository jpaTerminationRepository(
      TerminationEntityRepository terminationEntityRepository) {
    return new JpaTerminationRepository(terminationEntityRepository);
  }
}
