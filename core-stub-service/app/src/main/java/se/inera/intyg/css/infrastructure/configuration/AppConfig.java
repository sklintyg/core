package se.inera.intyg.css.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.inera.intyg.css.application.service.IntygstjanstService;
import se.inera.intyg.css.infrastructure.persistence.IntygstjanstRepository;

@Configuration
@EnableScheduling
public class AppConfig {

  @Bean
  public IntygstjanstRepository intygstjanstRepository() {
    return new IntygstjanstRepository();
  }

  @Bean
  public IntygstjanstService intygstjanstService(IntygstjanstRepository intygstjanstRepository) {
    return new IntygstjanstService(intygstjanstRepository);
  }
}
