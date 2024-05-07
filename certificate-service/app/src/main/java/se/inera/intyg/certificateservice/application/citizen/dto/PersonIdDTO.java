package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.PersonIdDTO.PersonIdDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = PersonIdDTOBuilder.class)
public class PersonIdDTO {

  String type;
  String id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonIdDTOBuilder {

  }

}