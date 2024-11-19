package se.inera.intyg.certificateservice.application.message.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageRequest.GetCertificateFromMessageRequestBuilder;

@JsonDeserialize(builder = GetCertificateFromMessageRequestBuilder.class)
@Value
@Builder
public class GetCertificateFromMessageRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateFromMessageRequestBuilder {

  }
}
