package se.inera.intyg.intygproxyservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V5;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.DECEASED_TEST_INDICATED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON_DTO;

import io.github.microcks.testcontainers.MicrocksContainer;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;
import se.inera.intyg.intygproxyservice.integrationtest.util.ApiUtil;
import se.inera.intyg.intygproxyservice.integrationtest.util.Containers;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;

@ActiveProfiles({"integration-test", PU_PROFILE_V5, "dev"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TestIndicatedClassificationIT {

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;

  @Autowired
  public TestIndicatedClassificationIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeAll
  static void beforeAll() {
    Containers.ensureRunning();
    final var microcks = new MicrocksContainer(
        DockerImageName.parse("quay.io/microcks/microcks-uber:1.8.1"))
        .withMainArtifacts("soapui/GetPersonsForProfileResponder-5.0.xml");

    final var redis = new GenericContainer<>(
        DockerImageName.parse("redis:6.0.9-alpine")
    ).withExposedPorts(6379);

    microcks.start();
    microcks.followOutput(new Slf4jLogConsumer(LoggerFactory.getLogger("MicrocksContainerLogs")));

    redis.start();

    System.setProperty("integration.pu.getpersonsforprofile.endpoint",
        microcks.getSoapMockEndpoint("GetPersonsForProfile", "5.0")
    );
    System.setProperty("integration.pu.cache.seconds", "86400");

    System.setProperty("putjanst.testindicated.reclassify.active.except.ssn",
        String.format("191212121212, %s",
            PROTECTED_PERSON_DTO.getPersonnummer()
        )
    );
  }

  @AfterAll
  static void afterAll() {
    System.out.println("DONE!");
  }

  @AfterEach
  void tearDown() throws IOException, InterruptedException {
    Containers.REDIS_CONTAINER.execInContainer("redis-cli", "flushall");
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @Nested
  class ClassifyTestIndicated {

    @Test
    void shallSwapTestIndicatedFlagToFalseFromTrueToIfReclassifyIsSetToNotIncludeId() {
      final var request = PersonRequest.builder()
          .personId(DECEASED_TEST_INDICATED_PERSON.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertFalse(response.getBody().getPerson().isTestIndicator())
      );
    }

    @Test
    void shallSwapTestIndicatedFlagToTrueFromFalseIfReclassifyIsSetToIncludeId() {
      final var request = PersonRequest.builder()
          .personId(PROTECTED_PERSON_DTO.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertTrue(response.getBody().getPerson().isTestIndicator())
      );
    }
  }
}
