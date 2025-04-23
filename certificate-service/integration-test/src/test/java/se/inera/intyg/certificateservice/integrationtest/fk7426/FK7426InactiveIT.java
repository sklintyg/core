package se.inera.intyg.certificateservice.integrationtest.fk7426;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.InactiveTypeIT;

class FK7426InactiveIT {

  private static final String CERTIFICATE_TYPE = FK7426Constants.FK7426;
  private static final String ACTIVE_VERSION = FK7426Constants.VERSION;
  private static final String TYPE = FK7426Constants.TYPE;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7427.v1_0.active.from", () -> "2099-01-01T00:00:00");
  }

  @Nested
  @DisplayName(TYPE + "Inaktivt intyg")
  class InactiveType extends InactiveTypeIT {

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