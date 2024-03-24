package se.inera.intyg.certificateservice.integrationtest.util;

import org.testcontainers.activemq.ActiveMQContainer;

public class Containers {

  public static ActiveMQContainer AMQ_CONTAINER;

  public static void ensureRunning() {
    redisContainer();
  }

  private static void redisContainer() {
    if (AMQ_CONTAINER == null) {
      AMQ_CONTAINER = new ActiveMQContainer("apache/activemq-classic:5.18.3")
          .withUser("activemqUser")
          .withPassword("activemqPassword")
          .withEnv("ANONYMOUS_LOGIN", "true");
    }

    if (!AMQ_CONTAINER.isRunning()) {
      AMQ_CONTAINER.start();
    }
  }

}

