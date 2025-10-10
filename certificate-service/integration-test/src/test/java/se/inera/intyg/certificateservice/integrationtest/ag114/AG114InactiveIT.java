package se.inera.intyg.certificateservice.integrationtest.ag114;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.integrationtest.InactiveTypeIT;

public class AG114InactiveIT {

  private static final String CERTIFICATE_TYPE = AG114Constants.AG114;
  private static final String TYPE = AG114Constants.TYPE;

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.ag114.v2_0.active.from", () -> "2099-01-01T00:00:00");
  }


  @Nested
  @Disabled
  @DisplayName(TYPE + "Inaktivt intyg")
  class InactiveType extends InactiveTypeIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return AG114Constants.VERSION;
    }
  }
}
