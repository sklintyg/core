package se.inera.intyg.certificateprintservice.playwright.browserpool;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class BrowserFactory extends BasePooledObjectFactory<PlaywrightBrowser> {

  @Override
  public PlaywrightBrowser create() {
    Map<String, String> env = new HashMap<>();
    //env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
    env.put("PLAYWRIGHT_BROWSERS_PATH",
        //Paths.get("C:\\Utveckling\\playwright-browsers").toString());
        Paths.get("/browsers").toString());
    final var playwright = Playwright.create(new Playwright.CreateOptions().setEnv(env));
    final var launchOptions = new LaunchOptions()
        .setHeadless(true);
    final var browser = playwright.chromium().launch(launchOptions);
    return new PlaywrightBrowser(playwright, browser);
  }

  @Override
  public PooledObject<PlaywrightBrowser> wrap(PlaywrightBrowser browser) {
    return new DefaultPooledObject<>(browser);
  }

}
