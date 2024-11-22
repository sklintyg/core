package se.inera.intyg.intygproxyservice.person.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO.PersonDTOBuilder;

@JsonDeserialize(builder = PersonDTOBuilder.class)
@Data
@Builder
public class PersonDTO implements Serializable {

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

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonDTOBuilder {

  }
}
