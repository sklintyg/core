package se.inera.intyg.certificateservice.integrationtest.ts8071;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.ExistsCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCitizenCertificateListIT;

class TS8071CitizenIT {

  private static final String CERTIFICATE_TYPE = TS8071Constants.TS8071;
  private static final String ACTIVE_VERSION = TS8071Constants.VERSION;
  private static final String TYPE = TS8071Constants.TYPE;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.ts8071.v1_0.active.from", () -> "2024-01-01T00:00:00");
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

  @Nested
  @DisplayName(TYPE + "Finns intyg för invånare")
  class ExistsCitizenCertificate extends ExistsCitizenCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }
}
