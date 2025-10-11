package se.inera.intyg.certificateservice.integrationtest.ag114;

import static se.inera.intyg.certificateservice.integrationtest.ag114.AG114TestSetup.ag114TestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import se.inera.intyg.certificateservice.integrationtest.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.ExistsCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateListIT;
import se.inera.intyg.certificateservice.integrationtest.TestabilityUtilities;

class AG114CitizenIT extends ActiveCertificatesIT {

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
  @DisplayName(TYPE + "Hämta intyg för invånare")
  class GetCitizenCertificate extends GetCitizenCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygslista för invånare")
  class GetCitizenCertificateList extends GetCitizenCertificateListIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Finns intyg för invånare")
  class ExistsCitizenCertificate extends ExistsCitizenCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }
}
