package se.inera.intyg.certificateservice.integrationtest.ag7804;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.InactiveTypeIT;

class AG7804InactiveIT {

  private static final String CERTIFICATE_TYPE = AG7804Constants.AG7804;
  private static final String ACTIVE_VERSION = AG7804Constants.VERSION;
  private static final String TYPE = AG7804Constants.TYPE;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.ag7804.v2_0.active.from", () -> "2099-01-01T00:00:00");
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