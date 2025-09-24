package se.inera.intyg.certificateanalyticsservice.integrationtest;

import static se.inera.intyg.certificateanalyticsservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.Containers;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.InternalApiUtil;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.TestListener;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.TestabilityApiUtil;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationIT {

  @LocalServerPort
  protected int port;

  @Autowired
  protected TestRestTemplate restTemplate;
  protected static final TestListener testListener = new TestListener();

  protected ApiUtil api;
  protected InternalApiUtil internalApi;
  protected TestabilityApiUtil testabilityApi;

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
    this.internalApi = new InternalApiUtil(restTemplate, port);
    this.testabilityApi = new TestabilityApiUtil(restTemplate, port);
    testListener.emptyMessages();
  }

  @BeforeAll
  public static void beforeAll() {
    Containers.ensureRunning();
  }

  @AfterEach
  void tearDown() {
    testabilityApi.reset();
    api.reset();
    internalApi.reset();
  }
}
