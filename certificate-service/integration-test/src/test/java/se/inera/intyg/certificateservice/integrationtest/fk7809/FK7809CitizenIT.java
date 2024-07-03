package se.inera.intyg.certificateservice.integrationtest.fk7809;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateListIT;

class FK7809CitizenIT {

  private static final String CERTIFICATE_TYPE = FK7809Constants.FK7809;
  private static final String ACTIVE_VERSION = FK7809Constants.VERSION;
  private static final String TYPE = FK7809Constants.TYPE;

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

  //TODO Uncomment print test class when pdf functionality has been implemented
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
