package se.inera.intyg.certificateservice.integrationtest.util;

import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class Containers {

  public static RabbitMQContainer AMQ_CONTAINER;

  public static void ensureRunning() {
    redisContainer();
  }

  private static void redisContainer() {
    if (AMQ_CONTAINER == null) {
      AMQ_CONTAINER = new RabbitMQContainer(
          DockerImageName.parse("rabbitmq:3.7.25-management-alpine")
      ).withExposedPorts(5672).withPluginsEnabled("rabbitmq_management");
    }

    if (!AMQ_CONTAINER.isRunning()) {
      AMQ_CONTAINER.start();
    }
  }

}

