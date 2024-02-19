package se.inera.intyg.certificateservice.domain.unit.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UnitContactInfo {

  String phoneNumber;
  String email;
}
