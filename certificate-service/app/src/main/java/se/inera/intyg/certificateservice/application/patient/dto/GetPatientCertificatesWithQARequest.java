package se.inera.intyg.certificateservice.application.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQARequest.GetPatientCertificatesWithQARequestBuilder;

@Value
@Builder
@JsonDeserialize(builder = GetPatientCertificatesWithQARequestBuilder.class)
public class GetPatientCertificatesWithQARequest {

  PersonIdDTO personId;
  List<String> unitIds;
  String careProviderId;
  LocalDateTime fromDate;
  LocalDateTime toDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetPatientCertificatesWithQARequestBuilder {

  }
}
