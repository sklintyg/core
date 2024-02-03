package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO.CertificateModelIdDTOBuilder;

@JsonDeserialize(builder = CertificateModelIdDTOBuilder.class)
@Value
@Builder
public class CertificateModelIdDTO {

  String type;
  String version;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateModelIdDTOBuilder {

  }
}
