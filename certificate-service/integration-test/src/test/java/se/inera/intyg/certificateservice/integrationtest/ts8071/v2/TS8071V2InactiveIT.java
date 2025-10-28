package se.inera.intyg.certificateservice.integrationtest.ts8071.v2;

import static se.inera.intyg.certificateservice.integrationtest.ts8071.v2.TS8071V2TestSetup.ACTIVE_CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateservice.integrationtest.ts8071.v2.TS8071V2TestSetup.ts8071V2TestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.InActiveCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.tests.InactiveTypeIT;
import se.inera.intyg.certificateservice.integrationtest.ts8071.v1.TS8071TestSetup;

class TS8071V2InactiveIT extends InActiveCertificatesIT {

  public static final String TYPE = TS8071TestSetup.TYPE;

  @DynamicPropertySource
  static void deactivateV1(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.ts8071.v1_0.active.from", () -> "2099-01-01T00:00:00");
  }

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = ts8071V2TestSetup()
        .testabilityUtilities(
            TestabilityUtilities.builder()
                .api(api)
                .internalApi(internalApi)
                .testabilityApi(testabilityApi)
                .build()
        )
        .build();
  }

  @AfterEach
  void tearDown() {
    super.tearDownBaseIT();
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Inaktivt intyg")
  class InactiveType extends InactiveTypeIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }
}
