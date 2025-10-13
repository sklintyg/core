package se.inera.intyg.certificateservice.integrationtest.ag114;

import static se.inera.intyg.certificateservice.integrationtest.ag114.AG114TestSetup.ag114TestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.InActiveCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.tests.InactiveTypeIT;

class AG114InactiveIT extends InActiveCertificatesIT {

  public static final String TYPE = AG114TestSetup.TYPE;

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = ag114TestSetup()
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
  @DisplayName(TYPE + "Inaktivt intyg")
  class InactiveType extends InactiveTypeIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }
}
