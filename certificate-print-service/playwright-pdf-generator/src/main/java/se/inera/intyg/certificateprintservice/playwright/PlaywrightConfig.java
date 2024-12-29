package se.inera.intyg.certificateprintservice.playwright;

import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaywrightConfig {

  private Playwright playwrightCertificateDetails;
  private Playwright playwrightCertificateInfo;

  @Bean
  @Qualifier("playwrightCertificateDetails")
  Playwright playwrightCertificateDetails() {
    this.playwrightCertificateDetails = Playwright.create();
    return this.playwrightCertificateDetails;
  }

  @Bean
  @Qualifier("playwrightCertificateInfo")
  Playwright playwrightCertificateInfo() {
    this.playwrightCertificateInfo = Playwright.create();
    return this.playwrightCertificateInfo;
  }

  @PreDestroy
  void destroy() {
    playwrightCertificateDetails.close();
    playwrightCertificateInfo.close();
  }

}
