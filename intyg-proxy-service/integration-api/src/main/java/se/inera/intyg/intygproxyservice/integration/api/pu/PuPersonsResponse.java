package se.inera.intyg.intygproxyservice.integration.api.pu;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PuPersonsResponse {

  List<PuResponse> persons;

  public static PuPersonsResponse empty() {
    return PuPersonsResponse.builder()
        .persons(Collections.emptyList())
        .build();
  }

  public static PuPersonsResponse error() {
    return PuPersonsResponse.builder()
        .persons(
            List.of(PuResponse.error())
        )
        .build();
  }
}
