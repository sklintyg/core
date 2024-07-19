package se.inera.intyg.certificateservice.application.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQAResponse.PatientCertificatesWithQAResponseBuilder;

@Value
@Builder
@JsonDeserialize(builder = PatientCertificatesWithQAResponseBuilder.class)
public class PatientCertificatesWithQAResponse {

  String list;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PatientCertificatesWithQAResponseBuilder {

  }
}
