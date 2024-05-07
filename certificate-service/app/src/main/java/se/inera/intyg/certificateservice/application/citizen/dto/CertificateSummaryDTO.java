package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateSummaryDTO.CertificateSummaryDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificateSummaryDTOBuilder.class)
public class CertificateSummaryDTO {

  String label;
  String value;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateSummaryDTOBuilder {

  }

}