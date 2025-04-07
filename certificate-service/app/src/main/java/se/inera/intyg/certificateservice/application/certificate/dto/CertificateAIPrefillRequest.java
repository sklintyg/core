package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateAIPrefillRequest.CertificateAIPrefillRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = CertificateAIPrefillRequestBuilder.class)
@Value
@Builder
public class CertificateAIPrefillRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  String preFillData;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateAIPrefillRequestBuilder {

  }
}
