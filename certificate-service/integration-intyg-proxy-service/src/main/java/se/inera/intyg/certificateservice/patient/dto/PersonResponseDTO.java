package se.inera.intyg.certificateservice.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.patient.dto.PersonResponseDTO.PersonResponseDTOBuilder;

@JsonDeserialize(builder = PersonResponseDTOBuilder.class)
@Value
@Builder
public class PersonResponseDTO {

  PersonDTO person;
  StatusDTOType status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonResponseDTOBuilder {

  }
}