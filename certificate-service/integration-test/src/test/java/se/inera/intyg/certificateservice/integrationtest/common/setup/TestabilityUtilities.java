package se.inera.intyg.certificateservice.integrationtest.common.setup;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.integrationtest.common.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.common.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.common.util.TestabilityApiUtil;

@Value
@Builder
public class TestabilityUtilities {

  ApiUtil api;
  InternalApiUtil internalApi;
  TestabilityApiUtil testabilityApi;
}
