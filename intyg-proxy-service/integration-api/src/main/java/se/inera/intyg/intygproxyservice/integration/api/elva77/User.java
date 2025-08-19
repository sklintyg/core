package se.inera.intyg.intygproxyservice.integration.api.elva77;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

  String personnummer;
  String fornamn;
  String efternamn;
  String postadress;
  String postnummer;
  String postort;
  boolean isActive;

  public static User inactive(String personId) {
    return User.builder()
        .personnummer(personId)
        .isActive(false)
        .build();
  }

}