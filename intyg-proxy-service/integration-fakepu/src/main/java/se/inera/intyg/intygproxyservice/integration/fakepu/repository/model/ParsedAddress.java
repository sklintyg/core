package se.inera.intyg.intygproxyservice.integration.fakepu.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedAddress {

  String careOf;
  String postalAddress1;
  String postalAddress2;
  String postalCode;
  String city;
}
