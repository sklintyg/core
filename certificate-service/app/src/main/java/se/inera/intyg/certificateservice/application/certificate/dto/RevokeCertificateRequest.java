package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest.RevokeCertificateRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = RevokeCertificateRequestBuilder.class)
@Value
@Builder
public class RevokeCertificateRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  RevokeInformationDTO revoked;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RevokeCertificateRequestBuilder {

  }
}