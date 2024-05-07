package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateDTO.CitizenCertificateDTOBuilder;

@JsonDeserialize(builder = CitizenCertificateDTOBuilder.class)
@Value
@Builder
public class CitizenCertificateDTO {

  CertificateMetadataDTO metadata;

  Map<String, CertificateDataElementDTO> data;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CitizenCertificateDTOBuilder {

  }
}
