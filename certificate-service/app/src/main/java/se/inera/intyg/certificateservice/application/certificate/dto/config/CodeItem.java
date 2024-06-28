package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CodeItem.CodeItemBuilder;

@JsonDeserialize(builder = CodeItemBuilder.class)
@Value
@Builder
public class CodeItem {

  String id;
  String label;
  String code;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CodeItemBuilder {

  }
}
