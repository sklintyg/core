package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateEventDTO.CertificateEventDTOBuilder;

@JsonDeserialize(builder = CertificateEventDTOBuilder.class)
@Value
@Builder
public class CertificateEventDTO {

  String certificateId;
  LocalDateTime timestamp;
  CertificateEventTypeDTO type;
  String relatedCertificateId;
  CertificateStatusTypeDTO relatedCertificateStatus;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateEventDTOBuilder {

  }
}
