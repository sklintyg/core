package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportCertificateInternalRequest.ExportCertificateInternalRequestBuilder;

@Value
@Builder
@JsonDeserialize(builder = ExportCertificateInternalRequestBuilder.class)
public class ExportCertificateInternalRequest {

  int page;
  int size;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ExportCertificateInternalRequestBuilder {

  }
}