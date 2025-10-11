package se.inera.intyg.certificateservice.integrationtest;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BaseTestabilityUtilities {

  TestabilityUtilities testabilityUtilities;
  TestabilityCertificate testabilityCertificate;
}
