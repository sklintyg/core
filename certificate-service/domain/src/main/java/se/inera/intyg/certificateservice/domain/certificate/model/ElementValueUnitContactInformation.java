package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementValueUnitContactInformation implements ElementValue {

  String address;
  String city;
  String zipCode;
  String phoneNumber;

}
