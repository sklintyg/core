package se.inera.intyg.certificateservice.domain.unit.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UnitAddress {

  String address;
  String zipCode;
  String city;
}
