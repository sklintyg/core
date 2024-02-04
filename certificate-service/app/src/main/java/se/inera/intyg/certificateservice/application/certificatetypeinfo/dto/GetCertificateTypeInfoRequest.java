package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest.GetCertificateTypeInfoRequestBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

@JsonDeserialize(builder = GetCertificateTypeInfoRequestBuilder.class)
@Value
@Builder
public class GetCertificateTypeInfoRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateTypeInfoRequestBuilder {

  }
}
