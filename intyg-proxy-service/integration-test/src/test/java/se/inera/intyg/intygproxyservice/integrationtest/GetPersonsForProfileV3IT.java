package se.inera.intyg.intygproxyservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V3;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.DECEASED_TEST_INDICATED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON;
import static se.inera.intyg.intygproxyservice.integrationtest.TestDataPatient.PROTECTED_PERSON_DTO;

import io.github.microcks.testcontainers.MicrocksContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.DockerImageName;
import se.inera.intyg.intygproxyservice.integrationtest.util.ApiUtil;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;

@ActiveProfiles({"integration-test", PU_PROFILE_V3})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetPersonsForProfileV3IT {

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;

  @Autowired
  public GetPersonsForProfileV3IT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeAll
  static void beforeAll() {
    final var microcks = new MicrocksContainer(
        DockerImageName.parse("quay.io/microcks/microcks-uber:1.8.1"))
        .withMainArtifacts("soapui/GetpersonsForProfileResponder-3.1.xml");

    microcks.start();

    System.setProperty("integration.pu.getpersonsforprofile.endpoint",
        microcks.getSoapMockEndpoint("GetPersonsForProfile", "3.1")
    );
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @Test
  void shallReturnTestPerson() {
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
  void shallReturnDeceasedTestIndicatedPerson() {
    final var request = PersonRequest.builder()
        .personId(PROTECTED_PERSON.getPersonnummer())
        .build();

    final var response = api.person(request);

    assertAll(
        () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> assertEquals(PROTECTED_PERSON_DTO, response.getBody().getPerson())
    );
  }
}
