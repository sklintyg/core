package se.inera.intyg.certificateservice.integrationtest.fk3226;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateListIT;

class FK3226CitizenIT {

  private static final String CERTIFICATE_TYPE = FK3226Constants.FK3226;
  private static final String ACTIVE_VERSION = FK3226Constants.VERSION;
  private static final String TYPE = FK3226Constants.TYPE;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk3226.v1_0.active.from", () -> "2024-01-01T00:00:00");
  }

  @Nested
  @DisplayName(TYPE + "Hämta intyg för invånare")
  class GetCitizenCertificate extends GetCitizenCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygslista för invånare")
  class GetCitizenCertificateList extends GetCitizenCertificateListIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  //TODO Uncomment print tests when pdf functionality has been implemented
  /*@Nested
  @DisplayName(TYPE + "Skriv ut intyg för invånare")
  class PrintCitizenCertificate extends PrintCitizenCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }*/
}
