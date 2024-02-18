package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest.GetUnitCertificatesRequestBuilder;

@JsonDeserialize(builder = GetUnitCertificatesRequestBuilder.class)
@Value
@Builder
public class GetUnitCertificatesRequest {

  PatientDTO patient;
  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  CertificatesQueryCriteriaDTO certificatesQueryCriteria;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetUnitCertificatesRequestBuilder {

  }
}
