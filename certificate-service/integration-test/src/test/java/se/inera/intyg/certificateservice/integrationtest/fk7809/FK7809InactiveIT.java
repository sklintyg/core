package se.inera.intyg.certificateservice.integrationtest.fk7809;

import static se.inera.intyg.certificateservice.integrationtest.fk7809.FK7809TestSetup.fk7809TestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.InActiveCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.tests.InactiveTypeIT;

class FK7809InactiveIT extends InActiveCertificatesIT {

  public static final String TYPE = FK7809TestSetup.TYPE;

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = fk7809TestSetup()
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
