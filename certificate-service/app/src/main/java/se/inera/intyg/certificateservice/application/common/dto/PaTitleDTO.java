package se.inera.intyg.certificateservice.application.common.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PaTitleDTO.PaTitleDTOBuilder;

@JsonDeserialize(builder = PaTitleDTOBuilder.class)
@Value
@Builder
public class PaTitleDTO {

  String code;
  String description;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PaTitleDTOBuilder {

  }
}
