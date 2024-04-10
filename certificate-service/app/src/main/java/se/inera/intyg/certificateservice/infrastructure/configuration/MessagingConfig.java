package se.inera.intyg.certificateservice.infrastructure.configuration;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import java.util.List;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class MessagingConfig {

  @Value("${certificate.event.queue.name}")
  private String eventQueueName;

  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Value("${spring.activemq.user}")
  private String amqUser;

  @Value("${spring.activemq.password}")
  private String amqPassword;

  @Bean
  public Queue eventQueue() {
    return new ActiveMQQueue(eventQueueName);
  }

  @Bean
  public ConnectionFactory cachingConnectionFactory() {
    final var activeMqConnectionFactory = new ActiveMQConnectionFactory(amqUser, amqPassword,
        brokerUrl);
    activeMqConnectionFactory.setTrustedPackages(
        List.of("se.inera.intyg.certificateservice.infrastructure.messaging")
    );
    return new CachingConnectionFactory(activeMqConnectionFactory);
  }

  @Bean
  public JmsTemplate eventJmsTemplate() {
    final var jmsTemplate = new JmsTemplate(cachingConnectionFactory());
    jmsTemplate.setDefaultDestination(eventQueue());
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }
}
