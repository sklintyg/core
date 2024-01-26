package se.inera.intyg.certificateservice.domain.user.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

  boolean blocked;
}
