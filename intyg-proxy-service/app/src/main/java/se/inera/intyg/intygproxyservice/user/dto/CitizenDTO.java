package se.inera.intyg.intygproxyservice.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.intygproxyservice.user.dto.CitizenDTO.CitizenDTOBuilder;

@JsonDeserialize(builder = CitizenDTOBuilder.class)
@Value
@Builder
public class CitizenDTO {

  String personnummer;
  String fornamn;
  String efternamn;
  String postadress;
  String postnummer;
  String postort;
  boolean isActive;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CitizenDTOBuilder {

  }
}