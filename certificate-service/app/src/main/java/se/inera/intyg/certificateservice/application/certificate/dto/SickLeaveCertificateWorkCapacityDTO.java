package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateWorkCapacityDTO.SickLeaveCertificateWorkCapacityDTOBuilder;

@JsonDeserialize(builder = SickLeaveCertificateWorkCapacityDTOBuilder.class)
@Value
@Builder
public class SickLeaveCertificateWorkCapacityDTO {

  Integer capacityPercentage;
  String fromDate;
  String toDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SickLeaveCertificateWorkCapacityDTOBuilder {

  }
}