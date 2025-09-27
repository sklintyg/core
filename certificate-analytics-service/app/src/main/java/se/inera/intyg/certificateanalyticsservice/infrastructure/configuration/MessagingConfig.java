package se.inera.intyg.certificateanalyticsservice.infrastructure.configuration;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class MessagingConfig {

  @Value("${certificate.analytics.message.queue.name}")
  private String eventQueueName;

  @Bean
  public Queue eventQueue() {
    return new ActiveMQQueue(eventQueueName);
  }

}
