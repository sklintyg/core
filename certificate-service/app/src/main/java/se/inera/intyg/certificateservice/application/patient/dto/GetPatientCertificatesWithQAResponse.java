package se.inera.intyg.certificateservice.application.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQAResponse.GetPatientCertificatesWithQAResponseBuilder;

@Value
@Builder
@JsonDeserialize(builder = GetPatientCertificatesWithQAResponseBuilder.class)
public class GetPatientCertificatesWithQAResponse {

  String list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetPatientCertificatesWithQAResponseBuilder {

  }
}
