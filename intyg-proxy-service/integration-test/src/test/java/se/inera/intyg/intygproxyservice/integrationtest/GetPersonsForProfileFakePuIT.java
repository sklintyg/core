package se.inera.intyg.intygproxyservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.FAKE_PU_PROFILE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.intygproxyservice.integrationtest.util.ApiUtil;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;

@ActiveProfiles({"integration-test", FAKE_PU_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetPersonsForProfileFakePuIT {

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;

  @Autowired
  public GetPersonsForProfileFakePuIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
  }

  @Test
  void shallReturnTestPerson() {
    final var request = PersonRequest.builder()
        .personId("194011306125")
        .build();

    final var response = api.person(request);

    assertAll(
        () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> assertEquals("194011306125", response.getBody().getPerson().getPersonnummer()),
        () -> assertEquals(Boolean.FALSE, response.getBody().getPerson().isTestIndicator())
    );
  }

  @Test
  void shallReturnTestIndicatedPerson() {
    final var request = PersonRequest.builder()
        .personId("194110299221")
        .build();

    final var response = api.person(request);

    assertAll(
        () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> assertEquals("194110299221", response.getBody().getPerson().getPersonnummer()),
        () -> assertEquals(Boolean.TRUE, response.getBody().getPerson().isTestIndicator())
    );
  }
}
