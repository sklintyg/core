package se.inera.intyg.intygproxyservice.integration.common;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@Slf4j
public class WebServiceClientFactory {

  @Value("${integration.ntjp.client.keystore.type:PKCS12}")
  private String keyStoreType;

  @Value("${integration.ntjp.client.keystore.password}")
  private String keyStorePassword;

  @Value("${integration.ntjp.client.keystore.path}")
  private String keyStorePath;

  @Value("${integration.ntjp.client.truststore.password}")
  private String trustStorePassword;

  @Value("${integration.ntjp.client.truststore.path}")
  private String trustStorePath;

  public <T> T create(Class<T> webserviceType, String endpoint) {
    final var jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
    jaxWsProxyFactoryBean.setAddress(endpoint);
    
    final var webserviceInstance = jaxWsProxyFactoryBean.create(webserviceType);

    final var client = ClientProxy.getClient(webserviceInstance);

    final var conduit = (HTTPConduit) client.getConduit();

    final var params = getTlsClientParameters();
    conduit.setTlsClientParameters(params);

    return webserviceInstance;
  }

  private TLSClientParameters getTlsClientParameters() {
    try {
      final var keyManagers = getKeyManagerFactory().getKeyManagers();
      final var trustManagers = getTrustManagerFactory().getTrustManagers();

      final var sslContext = SSLContext.getInstance("TLS");
      sslContext.init(keyManagers, trustManagers, null);

      final var params = new TLSClientParameters();
      params.setSSLSocketFactory(sslContext.getSocketFactory());
      return params;
    } catch (Exception ex) {
      log.error("Could not initialize sslContext!", ex);
      throw new IllegalStateException("Could not initialize sslContext!", ex);
    }
  }

  private KeyManagerFactory getKeyManagerFactory() {
    try {
      final var keyManagerFactory = KeyManagerFactory.getInstance(
          KeyManagerFactory.getDefaultAlgorithm());

      final var keyStore = KeyStore.getInstance(keyStoreType);
      keyStore.load(new FileInputStream(ResourceUtils.getFile(keyStorePath)),
          keyStorePassword.toCharArray());

      keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

      return keyManagerFactory;
    } catch (Exception ex) {
      log.error("Could not initialize keystore!", ex);
      throw new IllegalStateException("Could not initialize keystore!", ex);
    }
  }

  private TrustManagerFactory getTrustManagerFactory() {
    try {
      final var trustManagerFactory = TrustManagerFactory.getInstance(
          TrustManagerFactory.getDefaultAlgorithm());

      final var trustStore = KeyStore.getInstance(keyStoreType);
      trustStore.load(new FileInputStream((ResourceUtils.getFile(trustStorePath))),
          trustStorePassword.toCharArray());

      trustManagerFactory.init(trustStore);

      return trustManagerFactory;
    } catch (Exception ex) {
      log.error("Could not initialize truststore!", ex);
      throw new IllegalStateException("Could not initialize truststore!", ex);
    }
  }
}
