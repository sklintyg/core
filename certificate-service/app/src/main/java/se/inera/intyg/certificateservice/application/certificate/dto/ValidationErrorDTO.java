package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidationErrorDTO.ValidationErrorDTOBuilder;

@JsonDeserialize(builder = ValidationErrorDTOBuilder.class)
@Value
@Builder
public class ValidationErrorDTO {

  String id;
  String category;
  String field;
  String type;
  String text;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ValidationErrorDTOBuilder {

  }
}
