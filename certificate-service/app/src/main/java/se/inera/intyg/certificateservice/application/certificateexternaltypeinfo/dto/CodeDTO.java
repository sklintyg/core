package se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.CodeDTO.CodeDTOBuilder;

@JsonDeserialize(builder = CodeDTOBuilder.class)
@Value
@Builder
public class CodeDTO {

  String code;
  String codeSystem;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CodeDTOBuilder {

  }
}
