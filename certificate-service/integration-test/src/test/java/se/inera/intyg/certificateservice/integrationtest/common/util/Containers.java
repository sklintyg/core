package se.inera.intyg.certificateservice.integrationtest.common.util;

import org.testcontainers.activemq.ActiveMQContainer;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

public class Containers {

  public static ActiveMQContainer AMQ_CONTAINER;
  public static MockServerContainer MOCK_SERVER_CONTAINER;

  public static void ensureRunning() {
    amqContainer();
    mockServerContainer();
  }

  private static void amqContainer() {
    if (AMQ_CONTAINER == null) {
      AMQ_CONTAINER = new ActiveMQContainer("apache/activemq-classic:5.18.3")
          .withUser("activemqUser")
          .withPassword("activemqPassword");
    }

    if (!AMQ_CONTAINER.isRunning()) {
      AMQ_CONTAINER.start();
    }

    System.setProperty("spring.activemq.user", AMQ_CONTAINER.getUser());
    System.setProperty("spring.activemq.password", AMQ_CONTAINER.getPassword());
    System.setProperty("spring.activemq.broker-url", AMQ_CONTAINER.getBrokerUrl());
  }

  private static void mockServerContainer() {
    if (MOCK_SERVER_CONTAINER == null) {
      MOCK_SERVER_CONTAINER = new MockServerContainer(
          DockerImageName.parse("mockserver/mockserver:5.15.0")
      );
    }

    if (!MOCK_SERVER_CONTAINER.isRunning()) {
      MOCK_SERVER_CONTAINER.start();
    }

    final var mockServerContainerHost = MOCK_SERVER_CONTAINER.getHost();
    final var mockServerContainerPort = String.valueOf(MOCK_SERVER_CONTAINER.getServerPort());

    System.setProperty("integration.certificateprintservice.address",
        String.format("http://%s:%s", mockServerContainerHost, mockServerContainerPort)
    );

    System.setProperty("integration.intygproxyservice.address",
        String.format("http://%s:%s", mockServerContainerHost, mockServerContainerPort)
    );
  }
}