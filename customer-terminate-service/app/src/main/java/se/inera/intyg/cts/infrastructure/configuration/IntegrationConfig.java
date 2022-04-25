package se.inera.intyg.cts.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class IntegrationConfig {

  public static final int IN_MEMORY_SIZE_TO_MANAGE_LARGE_XML_RESPONSES = 16 * 1024 * 1024;

  @Bean
  public WebClient webClient() {
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs ->
            codecs.defaultCodecs().maxInMemorySize(IN_MEMORY_SIZE_TO_MANAGE_LARGE_XML_RESPONSES)
        )
        .build();
    return WebClient.builder()
        .exchangeStrategies(strategies)
        .build();
  }
}
