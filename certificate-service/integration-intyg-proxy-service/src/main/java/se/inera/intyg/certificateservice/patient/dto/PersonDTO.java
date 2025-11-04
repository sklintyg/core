package se.inera.intyg.certificateservice.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.patient.dto.PersonDTO.PersonDTOBuilder;

@JsonDeserialize(builder = PersonDTOBuilder.class)
@Value
@Builder
public class PersonDTO {

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