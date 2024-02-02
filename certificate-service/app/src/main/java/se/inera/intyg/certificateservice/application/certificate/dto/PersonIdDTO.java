package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO.PersonIdDTOBuilder;

@JsonDeserialize(builder = PersonIdDTOBuilder.class)
@Value
@Builder
public class PersonIdDTO {

  String type;
  String id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonIdDTOBuilder {

  }
}
