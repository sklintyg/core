package se.inera.intyg.certificateservice.domain.patient.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Name {

  String firstName;
  String lastName;
  String middleName;
  String fullName;
}
