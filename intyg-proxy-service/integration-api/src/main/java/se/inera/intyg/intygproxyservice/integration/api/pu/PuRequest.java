package se.inera.intyg.intygproxyservice.integration.api.pu;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PuRequest {

  String personId;
}
