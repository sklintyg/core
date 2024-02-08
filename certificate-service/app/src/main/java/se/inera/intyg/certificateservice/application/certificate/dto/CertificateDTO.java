package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO.CertificateDTOBuilder;

@JsonDeserialize(builder = CertificateDTOBuilder.class)
@Value
@Builder
public class CertificateDTO {

  CertificateMetadataDTO metadata;

  Map<String, CertificateDataElement> data;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDTOBuilder {

  }
}
