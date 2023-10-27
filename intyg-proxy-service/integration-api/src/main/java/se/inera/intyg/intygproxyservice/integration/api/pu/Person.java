package se.inera.intyg.intygproxyservice.integration.api.pu;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Person implements Serializable {

  String personnummer;
  boolean sekretessmarkering;
  boolean avliden;
  String fornamn;
  String mellannamn;
  String efternamn;
  String postadress;
  String postnummer;
  String postort;
  boolean testIndicator;
}
