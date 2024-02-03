package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO.PersonIdDTOBuilder;

@JsonDeserialize(builder = PersonIdDTOBuilder.class)
@Value
@Builder
public class PersonIdDTO {

  PersonIdTypeDTO type;
  String id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonIdDTOBuilder {

  }
}
