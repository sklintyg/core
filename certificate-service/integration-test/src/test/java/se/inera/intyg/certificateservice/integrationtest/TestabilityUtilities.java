package se.inera.intyg.certificateservice.integrationtest;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.TestListener;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;

@Value
@Builder
public class TestabilityUtilities {

  ApiUtil api;
  InternalApiUtil internalApi;
  TestabilityApiUtil testabilityApi;
  TestListener testListener;
}
