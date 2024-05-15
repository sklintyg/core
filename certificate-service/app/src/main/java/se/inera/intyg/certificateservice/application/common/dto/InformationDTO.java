package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.InformationDTO.InformationDTOBuilder;

@JsonDeserialize(builder = InformationDTOBuilder.class)
@Value
@Builder
public class InformationDTO {

  String id;
  String text;
  InformationType type;

  @JsonPOJOBuilder(withPrefix = "")
  public static class InformationDTOBuilder {

  }
}
