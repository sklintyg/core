package se.inera.intyg.certificateprintservice.playwright;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaywrightConfig {

  @Bean
  public BrowserPool browserPool() throws Exception {
    final var config = new GenericObjectPoolConfig<PlaywrightBrowser>();
    config.setMaxIdle(5);
    config.setMaxTotal(5);
    final var browserPool = new BrowserPool(new BrowserFactory(), config);
    browserPool.addObjects(5);
    return browserPool;
  }

}
