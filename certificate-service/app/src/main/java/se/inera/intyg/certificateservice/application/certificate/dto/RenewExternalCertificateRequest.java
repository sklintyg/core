package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest.RenewExternalCertificateRequestBuilder;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = RenewExternalCertificateRequestBuilder.class)
@Value
@Builder
public class RenewExternalCertificateRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;
  String externalReference;
  CertificateModelIdDTO certificateModelId;
  CertificateStatusTypeDTO status;
  PrefillXmlDTO prefillXml;
  UnitDTO issuingUnit;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RenewExternalCertificateRequestBuilder {

  }
}