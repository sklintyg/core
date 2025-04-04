package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.PrefillAICertificateRequest.PrefillAICertificateRequestBuilder;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = PrefillAICertificateRequestBuilder.class)
@Value
@Builder
public class PrefillAICertificateRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;
  CertificateModelIdDTO certificateModelId;
  String externalReference;
  String text;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrefillAICertificateRequestBuilder {

  }
}
