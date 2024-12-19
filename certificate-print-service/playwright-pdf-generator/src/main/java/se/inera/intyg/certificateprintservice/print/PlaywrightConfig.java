package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaywrightConfig {

  private Playwright playwright;

  @Bean()
  Playwright playwright() {
    this.playwright = Playwright.create();
    return this.playwright;
  }

  @PreDestroy
  void destroy() {
    playwright.close();
  }

}
