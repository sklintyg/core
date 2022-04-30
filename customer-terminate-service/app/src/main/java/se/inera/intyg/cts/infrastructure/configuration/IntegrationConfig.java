package se.inera.intyg.cts.infrastructure.configuration;

import io.netty.handler.ssl.SslContextBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class IntegrationConfig {

  public static final int IN_MEMORY_SIZE_TO_MANAGE_LARGE_XML_RESPONSES = 16 * 1024 * 1024;

  @Bean
  public WebClient webClient()
      throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, IOException, CertificateException {
    final var keyManagerFactory = KeyManagerFactory.getInstance(
        KeyManagerFactory.getDefaultAlgorithm());
    final var trustManagerFactory = TrustManagerFactory.getInstance(
        TrustManagerFactory.getDefaultAlgorithm());

    final var keyStore = KeyStore.getInstance("PKCS12");
    keyStore.load(new FileInputStream(ResourceUtils.getFile("classpath:localhost.p12")),
        "EdKSSx68zh".toCharArray());

    // Set up key manager factory to use our key store
    keyManagerFactory.init(keyStore, "EdKSSx68zh".toCharArray());

    // truststore
    final var trustStore = KeyStore.getInstance("PKCS12");
    trustStore.load(new FileInputStream((ResourceUtils.getFile("classpath:truststore.jks"))),
        "password".toCharArray());

    trustManagerFactory.init(trustStore);

    final var sslContext = SslContextBuilder
        .forClient()
        .keyManager(keyManagerFactory)
        .trustManager(trustManagerFactory)
        .build();

    final var httpClient = HttpClient.create().secure(sslSpec -> sslSpec.sslContext(sslContext));

    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs ->
            codecs.defaultCodecs().maxInMemorySize(IN_MEMORY_SIZE_TO_MANAGE_LARGE_XML_RESPONSES)
        )
        .build();

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(strategies)
        .build();
  }
}
