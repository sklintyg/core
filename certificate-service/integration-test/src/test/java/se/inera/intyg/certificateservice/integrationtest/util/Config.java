package se.inera.intyg.certificateservice.integrationtest.util;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@TestConfiguration
public class Config {

  @Autowired
  private Queue certificateEventQueue;

  @Bean
  ConnectionFactory jmsConnectionFactory() {
    Containers.ensureRunning();
    final var jmsBrokerUrl = System.getProperty("spring.activemq.broker-url");
    return new ActiveMQConnectionFactory(jmsBrokerUrl);
  }

  @Bean
  JmsTemplate certificateEventJmsTemplate() {
    final var jmsTemplate = new JmsTemplate(jmsConnectionFactory());
    jmsTemplate.setDefaultDestination(certificateEventQueue);
    jmsTemplate.setSessionTransacted(true);
    jmsTemplate.setReceiveTimeout(100);
    return jmsTemplate;
  }
}
