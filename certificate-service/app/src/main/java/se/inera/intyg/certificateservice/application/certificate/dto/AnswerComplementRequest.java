package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest.AnswerComplementRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = AnswerComplementRequestBuilder.class)
@Value
@Builder
public class AnswerComplementRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  String message;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AnswerComplementRequestBuilder {

  }
}
