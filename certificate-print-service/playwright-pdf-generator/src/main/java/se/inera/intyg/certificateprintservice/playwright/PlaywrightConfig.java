package se.inera.intyg.certificateprintservice.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaywrightConfig {

  private Playwright playwright;
  private Browser browser;

  @Bean()
  Browser browser(Playwright playwright) {
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    return browser;
  }

  @Bean()
  Playwright playwright() {
    this.playwright = Playwright.create();
    return this.playwright;
  }

  @PreDestroy
  void destroy() {
    browser.close();
    playwright.close();
  }

}
