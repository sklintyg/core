package se.inera.intyg.certificateservice.integrationtest.util;

import org.testcontainers.activemq.ActiveMQContainer;

public class Containers {

  public static ActiveMQContainer AMQ_CONTAINER;

  public static void ensureRunning() {
    activemqContainer();
  }

  private static void activemqContainer() {
    if (AMQ_CONTAINER == null) {
      AMQ_CONTAINER = new ActiveMQContainer("apache/activemq-classic:5.18.3");
    }

    if (!AMQ_CONTAINER.isRunning()) {
      AMQ_CONTAINER.start();
      final var host = AMQ_CONTAINER.getHost();
      final var port = AMQ_CONTAINER.getMappedPort(61616).toString();
      System.setProperty("spring.activemq.broker-url", "tcp://" + host + ":" + port);
    }
  }
}

