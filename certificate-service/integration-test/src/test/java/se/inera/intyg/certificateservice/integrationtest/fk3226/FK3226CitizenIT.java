package se.inera.intyg.certificateservice.integrationtest.fk3226;

import static se.inera.intyg.certificateservice.integrationtest.fk3226.FK3226TestSetup.fk3226TestSetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import se.inera.intyg.certificateservice.integrationtest.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.ExistsCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateListIT;
import se.inera.intyg.certificateservice.integrationtest.PrintCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.ag114.ActiveCertificatesIT;

class FK3226CitizenIT extends ActiveCertificatesIT {

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

  @Nested
  @DisplayName(TYPE + "Skriv ut intyg för invånare")
  class PrintCitizenCertificate extends PrintCitizenCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }
}
