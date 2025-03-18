package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportInternalResponse.ExportInternalResponseBuilder;

@JsonDeserialize(builder = ExportInternalResponseBuilder.class)
@Value
@Builder
public class ExportInternalResponse {

  List<ExportCertificateInternalResponse> exports;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ExportInternalResponseBuilder {

  }
}