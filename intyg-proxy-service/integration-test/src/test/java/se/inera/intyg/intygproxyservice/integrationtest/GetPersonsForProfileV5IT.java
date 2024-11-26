package se.inera.intyg.intygproxyservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.config.RedisConfig.PERSON_CACHE;
import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V5;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.DECEASED_TEST_INDICATED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.LILLTOLVAN;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON_DTO;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.TOLVAN;
import static se.inera.intyg.intygproxyservice.integrationtest.util.Containers.REDIS_CONTAINER;

import io.github.microcks.testcontainers.MicrocksContainer;
import java.io.IOException;
import java.util.List;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integrationtest.util.ApiUtil;
import se.inera.intyg.intygproxyservice.integrationtest.util.Containers;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;
import se.inera.intyg.intygproxyservice.person.dto.StatusDTOType;

@ActiveProfiles({"integration-test", PU_PROFILE_V5, "dev"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetPersonsForProfileV5IT {

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;

  @Autowired
  public GetPersonsForProfileV5IT(TestRestTemplate restTemplate) {
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
  }

  @AfterAll
  static void afterAll() {
    System.out.println("DONE!");
  }

  @AfterEach
  void tearDown() throws IOException, InterruptedException {
    REDIS_CONTAINER.execInContainer("redis-cli", "flushall");
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @Nested
  class GetPerson {

    @Test
    void shallReturnTestPerson() {
      final var request = PersonRequest.builder()
          .personId("195401782395")
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertEquals(PROTECTED_PERSON_DTO, response.getBody().getPerson())
      );
    }

    @Test
    void shallReturnDeceasedTestIndicatedPerson() {
      final var request = PersonRequest.builder()
          .personId(DECEASED_TEST_INDICATED_PERSON.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertEquals(DECEASED_TEST_INDICATED_PERSON, response.getBody().getPerson())
      );
    }

    @Test
    void shallNotSwapTestIndicatedFlagTrueToFalseIfReclassifyIdsAreNotSet() {
      final var request = PersonRequest.builder()
          .personId(DECEASED_TEST_INDICATED_PERSON.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertEquals(DECEASED_TEST_INDICATED_PERSON.isTestIndicator(),
              response.getBody().getPerson().isTestIndicator())
      );
    }

    @Test
    void shallNotSwapTestIndicatedFlagFalseToIfReclassifyIsNotSet() {
      final var request = PersonRequest.builder()
          .personId(PROTECTED_PERSON_DTO.getPersonnummer())
          .build();

      final var response = api.person(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertEquals(
              PROTECTED_PERSON_DTO.isTestIndicator(),
              response.getBody().getPerson().isTestIndicator()
          )
      );
    }
  }

  @Nested
  class GetPersons {

    @Test
    void shallReturnTestPersons() {
      final var request = PersonsRequest.builder()
          .personIds(List.of(LILLTOLVAN.getPersonnummer(), TOLVAN.getPersonnummer()))
          .build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
          () -> assertEquals(LILLTOLVAN, response.getBody().getPersons().getFirst().getPerson()),
          () -> assertEquals(StatusDTOType.FOUND,
              response.getBody().getPersons().getFirst().getStatus()),
          () -> assertEquals(TOLVAN, response.getBody().getPersons().get(1).getPerson()),
          () -> assertEquals(StatusDTOType.FOUND,
              response.getBody().getPersons().get(1).getStatus())
      );
    }

    @Test
    void shallReturnNotFoundIfNoResponseForPerson() {
      final var request = PersonsRequest.builder()
          .personIds(List.of(LILLTOLVAN.getPersonnummer(), TOLVAN.getPersonnummer(),
              PROTECTED_PERSON_DTO.getPersonnummer()))
          .build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              LILLTOLVAN,
              response.getBody().getPersons().getFirst().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().getFirst().getStatus()
          ),
          () -> assertEquals(
              TOLVAN,
              response.getBody().getPersons().get(1).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().get(1).getStatus()
          ),
          () -> assertEquals(
              PersonDTO.builder().personnummer(PROTECTED_PERSON_DTO.getPersonnummer()).build(),
              response.getBody().getPersons().get(2).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.NOT_FOUND,
              response.getBody().getPersons().get(2).getStatus())
      );
    }

    @Test
    void shallReturnPatientInCacheAndFromPu() throws IOException, InterruptedException {
      final var objectMapper = new ObjectMapper();
      final var cachedPuResponse = PuResponse.found(PROTECTED_PERSON);

      REDIS_CONTAINER.execInContainer(
          "redis-cli",
          "set",
          String.format("%s::%s", PERSON_CACHE, PROTECTED_PERSON_DTO.getPersonnummer()),
          objectMapper.writeValueAsString(cachedPuResponse)
      );

      final var request = PersonsRequest.builder()
          .personIds(
              List.of(
                  LILLTOLVAN.getPersonnummer(),
                  TOLVAN.getPersonnummer(),
                  PROTECTED_PERSON_DTO.getPersonnummer()
              )
          ).build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              LILLTOLVAN,
              response.getBody().getPersons().getFirst().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().getFirst().getStatus()
          ),
          () -> assertEquals(
              TOLVAN,
              response.getBody().getPersons().get(1).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().get(1).getStatus()
          ),
          () -> assertEquals(
              PROTECTED_PERSON_DTO,
              response.getBody().getPersons().get(2).getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.FOUND,
              response.getBody().getPersons().get(2).getStatus()
          )
      );
    }

    @Test
    void shallReturnErrorIfSeveralResponsesForPerson() {
      final var request = PersonsRequest.builder()
          .personIds(List.of(TOLVAN.getPersonnummer()))
          .build();

      final var response = api.persons(request);

      assertAll(
          () -> assertEquals(
              HttpStatus.OK,
              response.getStatusCode()
          ),
          () -> assertEquals(
              PersonDTO.builder().personnummer(TOLVAN.getPersonnummer()).build(),
              response.getBody().getPersons().getFirst().getPerson()
          ),
          () -> assertEquals(
              StatusDTOType.ERROR,
              response.getBody().getPersons().getFirst().getStatus()
          )
      );
    }
  }
}
