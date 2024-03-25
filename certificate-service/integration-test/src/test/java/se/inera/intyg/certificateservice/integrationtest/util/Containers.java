package se.inera.intyg.certificateservice.integrationtest.util;

import org.testcontainers.activemq.ActiveMQContainer;

public class Containers {

  public static ActiveMQContainer AMQ_CONTAINER;

  public static void ensureRunning() {
    amqContainer();
  }

  private static void amqContainer() {
    if (AMQ_CONTAINER == null) {
      AMQ_CONTAINER = new ActiveMQContainer("apache/activemq-classic:5.18.3")
          .withUser("activemqUser")
          .withPassword("activemqPassword")
          .withEnv("ANONYMOUS_LOGIN", "true")
          .withExposedPorts(5001);
    }

    System.setProperty("spring.activemq.user", "activemqUser");
    System.setProperty("spring.activemq.password", "activemqPassword");

    if (!AMQ_CONTAINER.isRunning()) {
      AMQ_CONTAINER.start();
    }
  }

}

