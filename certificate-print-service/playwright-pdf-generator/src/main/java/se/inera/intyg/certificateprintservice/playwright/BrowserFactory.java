package se.inera.intyg.certificateprintservice.playwright;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class BrowserFactory extends BasePooledObjectFactory<PlaywrightBrowser> {

  @Override
  public PlaywrightBrowser create() throws Exception {
    final var playwright = Playwright.create();
    final var browser = playwright.chromium().launch(new LaunchOptions().setHeadless(true));
    return new PlaywrightBrowser(playwright, browser);
  }

  @Override
  public PooledObject<PlaywrightBrowser> wrap(PlaywrightBrowser browser) {
    return new DefaultPooledObject<>(browser);
  }

}
