package se.inera.intyg.certificateservice.integrationtest.fk3226;

import static se.inera.intyg.certificateservice.integrationtest.fk3226.FK3226TestSetup.fk3226TestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import se.inera.intyg.certificateservice.integrationtest.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.InactiveTypeIT;
import se.inera.intyg.certificateservice.integrationtest.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.ag114.InActiveCertificatesIT;

class FK3226InactiveIT extends InActiveCertificatesIT {

  public static final String TYPE = FK3226TestSetup.TYPE;

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = fk3226TestSetup()
        .testabilityUtilities(
            TestabilityUtilities.builder()
                .api(api)
                .internalApi(internalApi)
                .testabilityApi(testabilityApi)
                .testListener(testListener)
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
