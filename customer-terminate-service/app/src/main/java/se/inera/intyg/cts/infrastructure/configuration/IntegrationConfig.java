package se.inera.intyg.cts.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatch;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateTexts;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.GetCertificateBatchFromIntygstjanst;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.GetCertificateTextsFromIntygstjanst;

@Configuration
public class IntegrationConfig {

  @Bean
  public GetCertificateBatch getCertificateBatch(WebClient webClient,
      @Value("${integration.intygsjanst.baseurl}") String baseUrl,
      @Value("${integration.intygsjanst.certificates.endpoint}") String certificatesEndpoint) {
    return new GetCertificateBatchFromIntygstjanst(webClient, baseUrl, certificatesEndpoint);
  }

  @Bean
  public GetCertificateTexts getCertificateTexts(WebClient webClient,
      @Value("${integration.intygsjanst.baseurl}") String baseUrl,
      @Value("${integration.intygsjanst.certificate.texts.endpoint}") String certificateTextsEndpoint) {
    return new GetCertificateTextsFromIntygstjanst(webClient, baseUrl, certificateTextsEndpoint);
  }

  @Bean
  public WebClient webClient() {
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();
    return WebClient.builder()
        .exchangeStrategies(strategies)
        .build();
  }
}
