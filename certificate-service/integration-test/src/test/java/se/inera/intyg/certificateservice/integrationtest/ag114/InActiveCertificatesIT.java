package se.inera.intyg.certificateservice.integrationtest.ag114;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.TestListener;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class InActiveCertificatesIT {

  @LocalServerPort
  protected int port;

  @Autowired
  protected TestRestTemplate restTemplate;
  protected static final TestListener testListener = new TestListener();

  protected ApiUtil api;
  protected InternalApiUtil internalApi;
  protected TestabilityApiUtil testabilityApi;

  protected BaseTestabilityUtilities baseTestabilityUtilities;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.ag114.v2_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.ag7804.v2_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk3221.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk3226.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk7210.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk7426.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk7427.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk7472.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk7804.v2_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk7809.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.fk7810.v1_0.active.from", () -> "2099-01-01T00:00:00");
    registry.add("certificate.model.ts8071.v1_0.active.from", () -> "2099-01-01T00:00:00");
  }

  @BeforeAll
  static void beforeAll() {
    Containers.ensureRunning();
  }

  protected void setUpBaseIT() {
    this.api = new ApiUtil(restTemplate, port);
    this.internalApi = new InternalApiUtil(restTemplate, port);
    this.testabilityApi = new TestabilityApiUtil(restTemplate, port);
    testListener.emptyMessages();
  }

  protected void tearDownBaseIT() {
    testabilityApi.reset();
    api.reset();
    internalApi.reset();
  }
}
