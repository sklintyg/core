package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateItemWorkCapacityDTO.SickLeaveCertificateItemWorkCapacityDTOBuilder;

@JsonDeserialize(builder = SickLeaveCertificateItemWorkCapacityDTOBuilder.class)
@Value
@Builder
public class SickLeaveCertificateItemWorkCapacityDTO {

  LocalDate startDate;
  LocalDate endDate;
  int reduction;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SickLeaveCertificateItemWorkCapacityDTOBuilder {

  }
}