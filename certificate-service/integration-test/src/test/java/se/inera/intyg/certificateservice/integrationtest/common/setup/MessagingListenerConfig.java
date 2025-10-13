package se.inera.intyg.certificateservice.integrationtest.common.setup;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.connection.CachingConnectionFactory;
import se.inera.intyg.certificateservice.integrationtest.common.util.MessageListenerUtil;

@TestConfiguration
@EnableJms
@Slf4j
public class MessagingListenerConfig {

  @Bean
  public MessageListener jmsMessageSpy() {
    final var messageListener = new MessageListener();
    MessageListenerUtil.setMessageListener(messageListener);
    return messageListener;
  }

  @Bean
  DisposableBean stopJmsListenersOnShutdown(JmsListenerEndpointRegistry registry) {
    return registry::stop;
  }

  @Bean
  BeanPostProcessor disableReconnectOnExceptionForTests() {
    return new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String name) {
        if (bean instanceof CachingConnectionFactory ccf) {
          ccf.setReconnectOnException(false);
        }
        return bean;
      }
    };
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory cf) {
    final var defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
    defaultJmsListenerContainerFactory.setConnectionFactory(cf);
    defaultJmsListenerContainerFactory.setPubSubDomain(false);
    defaultJmsListenerContainerFactory.setSessionTransacted(false);
    defaultJmsListenerContainerFactory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    defaultJmsListenerContainerFactory.setConcurrency("1");
    defaultJmsListenerContainerFactory.setReceiveTimeout(100L);
    return defaultJmsListenerContainerFactory;
  }
}