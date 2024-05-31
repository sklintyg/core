package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateRequest.ComplementCertificateRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = ComplementCertificateRequestBuilder.class)
@Value
@Builder
public class ComplementCertificateRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;
  String externalReference;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ComplementCertificateRequestBuilder {

  }
}