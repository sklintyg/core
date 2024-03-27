package se.inera.intyg.certificateservice.infrastructure.configuration;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class MessagingConfig {

  @Value("${certificate.service.amq.name}")
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
  public ConnectionFactory amqConnectionFactory() {
    return new ActiveMQConnectionFactory(amqUser, amqPassword, brokerUrl);
  }

  @Bean
  public ConnectionFactory cachingConnectionFactory() {
    return new CachingConnectionFactory(amqConnectionFactory());
  }

  @Bean
  public JmsTemplate eventJmsTemplate() {
    return jmsTemplate(cachingConnectionFactory(), eventQueue());
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory =
        new DefaultJmsListenerContainerFactory();
    factory
        .setConnectionFactory(amqConnectionFactory());

    return factory;
  }

  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Queue queue) {
    final var jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setDefaultDestination(queue);
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

}
