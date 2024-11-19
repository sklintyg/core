package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignRequest.CertificateReadyForSignRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = CertificateReadyForSignRequestBuilder.class)
@Value
@Builder
public class CertificateReadyForSignRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateReadyForSignRequestBuilder {

  }
}
