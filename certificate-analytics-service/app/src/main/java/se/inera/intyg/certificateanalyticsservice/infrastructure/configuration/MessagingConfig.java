package se.inera.intyg.certificateanalyticsservice.infrastructure.configuration;

import jakarta.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class MessagingConfig {

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
      ConnectionFactory connectionFactory,
      DefaultJmsListenerContainerFactoryConfigurer configurer) {
    final var defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
    configurer.configure(defaultJmsListenerContainerFactory, connectionFactory);
    defaultJmsListenerContainerFactory.setSessionTransacted(true);

    return defaultJmsListenerContainerFactory;
  }
}
