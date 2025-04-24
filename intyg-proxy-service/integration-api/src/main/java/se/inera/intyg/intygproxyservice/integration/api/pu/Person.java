package se.inera.intyg.intygproxyservice.integration.api.pu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person.PersonBuilder;

@Value
@Builder
@JsonDeserialize(builder = PersonBuilder.class)
public class Person implements Serializable {

  PersonId personnummer;
  boolean sekretessmarkering;
  boolean avliden;
  String fornamn;
  String mellannamn;
  String efternamn;
  String postadress;
  String postnummer;
  String postort;
  boolean testIndicator;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonBuilder {

  }
}