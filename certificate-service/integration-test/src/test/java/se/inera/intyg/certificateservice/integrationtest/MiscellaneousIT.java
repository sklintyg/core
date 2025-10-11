package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateservice.integrationtest.common.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.common.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.common.util.TestabilityApiUtil;

@ActiveProfiles({"integration-test"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MiscellaneousIT {

  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;
  private ApiUtil api;
  private TestabilityApiUtil testabilityApi;

  @Autowired
  public MiscellaneousIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @BeforeAll
  static void beforeAll() {
    Containers.ensureRunning();
  }

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
    this.testabilityApi = new TestabilityApiUtil(restTemplate, port);
  }

  @AfterEach
  void tearDown() {
    api.reset();
    testabilityApi.reset();
  }

  @Test
  @DisplayName("Om testability inte är aktiverat skall felkod 404 (NOT_FOUND) returneras")
  void shallReturn() {
    final var response = testabilityApi.addCertificate(
        defaultTestablilityCertificateRequest("fk7210", "1.0")
    );

    assertEquals(404, response.getStatusCode().value());
  }
}
