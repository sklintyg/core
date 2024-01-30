package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CareProvider {

  HsaId hsaId;
  String name;
  String address;
  String zipCode;
  String city;
  String phoneNumber;
  String email;
}
