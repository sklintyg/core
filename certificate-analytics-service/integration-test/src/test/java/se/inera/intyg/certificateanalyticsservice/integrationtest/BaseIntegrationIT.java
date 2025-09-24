package se.inera.intyg.certificateanalyticsservice.integrationtest;

import static se.inera.intyg.certificateanalyticsservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.Containers;
import se.inera.intyg.certificateanalyticsservice.integrationtest.util.TestListener;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationIT {

  @Autowired
  protected static final TestListener testListener = new TestListener();

  @BeforeEach
  void setUp() {
    testListener.emptyMessages();
  }

  @BeforeAll
  public static void beforeAll() {
    Containers.ensureRunning();
  }

  @AfterEach
  void tearDown() {
  }
}
