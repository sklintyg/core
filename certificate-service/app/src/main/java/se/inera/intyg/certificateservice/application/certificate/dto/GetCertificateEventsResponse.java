package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsResponse.GetCertificateEventsResponseBuilder;

@JsonDeserialize(builder = GetCertificateEventsResponseBuilder.class)
@Value
@Builder
public class GetCertificateEventsResponse {

  List<CertificateEventDTO> events;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateEventsResponseBuilder {

  }
}
