package se.inera.intyg.certificateservice.testability.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityCertificateRequest.TestabilityCertificateRequestBuilder;

@JsonDeserialize(builder = TestabilityCertificateRequestBuilder.class)
@Value
@Builder
public class TestabilityCertificateRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;
  CertificateModelIdDTO certificateModelId;
  TestabilityFillTypeDTO fillType;
  CertificateStatusTypeDTO status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class TestabilityCertificateRequestBuilder {

  }
}
