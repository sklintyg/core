package se.inera.intyg.intygproxyservice.integrationtest.util;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class Containers {

  public static GenericContainer<?> REDIS_CONTAINER;

  public static void ensureRunning() {
    redisContainer();
  }

  private static void redisContainer() {
    if (REDIS_CONTAINER == null) {
      REDIS_CONTAINER = new GenericContainer<>(
          DockerImageName.parse("redis:6.0.9-alpine")
      ).withExposedPorts(6379);
    }

    if (!REDIS_CONTAINER.isRunning()) {
      REDIS_CONTAINER.start();
    }

    System.setProperty("spring.data.redis.host", REDIS_CONTAINER.getHost());
    System.setProperty("spring.data.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
  }
}
