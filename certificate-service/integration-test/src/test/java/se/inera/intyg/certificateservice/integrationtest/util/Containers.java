package se.inera.intyg.certificateservice.integrationtest.util;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class Containers {

  public static GenericContainer<?> AMQ_CONTAINER;

  public static void ensureRunning() {
    redisContainer();
  }

  private static void redisContainer() {
    if (AMQ_CONTAINER == null) {
      AMQ_CONTAINER = new RabbitMQContainer(
          DockerImageName.parse("rabbitmq:3.7.25-management-alpine")
      ).withExposedPorts(5672);
    }

    if (!AMQ_CONTAINER.isRunning()) {
      AMQ_CONTAINER.start();
    }

    System.setProperty("spring.data.redis.host", AMQ_CONTAINER.getHost());
    System.setProperty("spring.data.redis.port", AMQ_CONTAINER.getMappedPort(5672).toString());
  }

}

