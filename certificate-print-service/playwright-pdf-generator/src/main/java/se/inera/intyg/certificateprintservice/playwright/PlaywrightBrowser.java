package se.inera.intyg.certificateprintservice.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaywrightBrowser {

  private final Playwright playwright;

  @Getter
  private final Browser browser;

}
