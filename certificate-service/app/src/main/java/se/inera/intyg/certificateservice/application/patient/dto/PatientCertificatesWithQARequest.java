package se.inera.intyg.certificateservice.application.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest.PatientCertificatesWithQARequestBuilder;

@Value
@Builder
@JsonDeserialize(builder = PatientCertificatesWithQARequestBuilder.class)
public class PatientCertificatesWithQARequest {

  PersonIdDTO personId;
  List<String> unitIds;
  String careProviderId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PatientCertificatesWithQARequestBuilder {

  }
}
