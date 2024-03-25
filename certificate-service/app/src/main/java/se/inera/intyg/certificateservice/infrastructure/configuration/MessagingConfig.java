package se.inera.intyg.certificateservice.infrastructure.configuration;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class MessagingConfig {

  @Value("${certificate.service.amq.name}")
  private String eventQueueName;

  @Bean
  public Queue eventQueue() {
    return new ActiveMQQueue(eventQueueName);
  }

  @Bean
  public ConnectionFactory jmsConnectionFactory() {
    return new ActiveMQConnectionFactory();
  }

  @Bean
  public JmsTemplate eventJmsTemplate() {
    return jmsTemplate(jmsConnectionFactory(), eventQueue());
  }

  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Queue queue) {
    final var jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setDefaultDestination(queue);
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

}
