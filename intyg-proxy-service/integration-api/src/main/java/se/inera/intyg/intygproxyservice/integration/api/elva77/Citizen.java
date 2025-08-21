package se.inera.intyg.intygproxyservice.integration.api.elva77;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Citizen {

  String subjectOfCareId;
  String firstname;
  String lastname;
  String streetAddress;
  String zip;
  String city;
  Boolean isActive;

  public static Citizen inactive(String personId) {
    return Citizen.builder()
        .subjectOfCareId(personId)
        .isActive(false)
        .build();
  }
}