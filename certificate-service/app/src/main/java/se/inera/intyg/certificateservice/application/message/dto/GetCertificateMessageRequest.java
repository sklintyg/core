package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageRequest.GetCertificateMessageRequestBuilder;

@JsonDeserialize(builder = GetCertificateMessageRequestBuilder.class)
@Value
@Builder
public class GetCertificateMessageRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateMessageRequestBuilder {

  }
}
