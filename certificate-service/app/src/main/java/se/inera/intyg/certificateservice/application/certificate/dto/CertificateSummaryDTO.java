package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateSummaryDTO.CertificateSummaryDTOBuilder;

@JsonDeserialize(builder = CertificateSummaryDTOBuilder.class)
@Value
@Builder
public class CertificateSummaryDTO {

  String label;
  String value;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateSummaryDTOBuilder {

  }
}