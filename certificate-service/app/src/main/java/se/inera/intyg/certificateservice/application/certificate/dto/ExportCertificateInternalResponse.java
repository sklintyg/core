package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportCertificateInternalResponse.ExportCertificateInternalResponseBuilder;

@JsonDeserialize(builder = ExportCertificateInternalResponseBuilder.class)
@Value
@Builder
public class ExportCertificateInternalResponse {

  String certificateId;
  String xml;
  boolean revoked;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ExportCertificateInternalResponseBuilder {

  }
}