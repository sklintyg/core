package se.inera.intyg.certificateservice.integrationtest.common.setup;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BaseTestabilityUtilities {

  TestabilityUtilities testabilityUtilities;
  TestabilityCertificate testabilityCertificate;
}
