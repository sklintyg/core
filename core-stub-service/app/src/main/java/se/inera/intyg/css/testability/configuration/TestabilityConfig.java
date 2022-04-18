package se.inera.intyg.css.testability.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.inera.intyg.css.infrastructure.persistence.IntygstjanstRepository;
import se.inera.intyg.css.testability.service.IntygstjanstTestabilityService;

@Configuration
@Profile("testability")
public class TestabilityConfig {

  @Bean
  public IntygstjanstTestabilityService intygstjanstTestabilityService(
      IntygstjanstRepository intygstjanstRepository) {
    return new IntygstjanstTestabilityService(intygstjanstRepository);
  }
}
