package se.inera.intyg.certificateservice.domain.common.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonId {

  String id;
  PersonIdType type;

  public String idWithoutDash() {
    return id.replace("-", "");
  }

  public String idWithDash() {
    if (id.contains("-")) {
      return id;
    }
    return String.join(
        "-",
        id.substring(0, 8),
        id.substring(8)
    );
  }
}
