package se.inera.intyg.intygproxyservice.integration.api.elva77;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Citizen {

  String personnummer;
  String fornamn;
  String efternamn;
  String postadress;
  String postnummer;
  String postort;
  boolean isActive;

  public static Citizen inactive(String personId) {
    return Citizen.builder()
        .personnummer(personId)
        .isActive(false)
        .build();
  }

}