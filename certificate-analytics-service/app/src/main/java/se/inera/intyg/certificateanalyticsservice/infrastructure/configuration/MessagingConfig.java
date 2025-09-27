package se.inera.intyg.certificateanalyticsservice.infrastructure.configuration;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class MessagingConfig {

  @Value("${certificate.analytics.message.queue.name}")
  private String eventQueueName;

  @Bean
  public Queue eventQueue() {
    return new ActiveMQQueue(eventQueueName);
  }

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
