package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.SendMessageRequest.SendMessageRequestBuilder;

@JsonDeserialize(builder = SendMessageRequestBuilder.class)
@Value
@Builder
public class SendMessageRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SendMessageRequestBuilder {

  }
}
