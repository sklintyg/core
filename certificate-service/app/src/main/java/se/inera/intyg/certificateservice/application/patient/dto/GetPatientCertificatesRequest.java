package se.inera.intyg.certificateservice.application.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest.GetPatientCertificatesRequestBuilder;

@JsonDeserialize(builder = GetPatientCertificatesRequestBuilder.class)
@Value
@Builder
public class GetPatientCertificatesRequest {

  PatientDTO patient;
  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetPatientCertificatesRequestBuilder {

  }
}
