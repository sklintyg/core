package se.inera.intyg.intygproxyservice.integration.api.pu;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PuPersonsRequest {

  List<String> personIds;
}
