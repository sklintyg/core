package se.inera.intyg.certificateservice.domain.patient.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonAddress {

  String street;
  String city;
  String zipCode;
}
