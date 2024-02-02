package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse.CreateCertificateResponseBuilder;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;

@JsonDeserialize(builder = CreateCertificateResponseBuilder.class)
@Value
@Builder
public class CreateCertificateResponse {

  CertificateDTO certificate;
  List<ResourceLinkDTO> links;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CreateCertificateResponseBuilder {

  }
}
