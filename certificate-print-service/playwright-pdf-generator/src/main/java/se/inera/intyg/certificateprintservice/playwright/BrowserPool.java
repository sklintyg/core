package se.inera.intyg.certificateprintservice.playwright;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class BrowserPool extends GenericObjectPool<PlaywrightBrowser> {

  public BrowserPool(PooledObjectFactory<PlaywrightBrowser> factory) {
    super(factory);
  }

  public BrowserPool(PooledObjectFactory<PlaywrightBrowser> factory,
      GenericObjectPoolConfig<PlaywrightBrowser> config) {
    super(factory, config);
  }

}
