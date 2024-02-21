package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO.CertificateDTOBuilder;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;

@JsonDeserialize(builder = CertificateDTOBuilder.class)
@Value
@Builder
public class CertificateDTO {

  @With
  CertificateMetadataDTO metadata;

  Map<String, CertificateDataElement> data;

  List<ResourceLinkDTO> links;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDTOBuilder {

  }
}
