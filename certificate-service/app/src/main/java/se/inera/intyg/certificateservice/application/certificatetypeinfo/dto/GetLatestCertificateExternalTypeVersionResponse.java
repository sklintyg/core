package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse.GetLatestCertificateExternalTypeVersionResponseBuilder;

@JsonDeserialize(builder = GetLatestCertificateExternalTypeVersionResponseBuilder.class)
@Value
@Builder
public class GetLatestCertificateExternalTypeVersionResponse {

  CertificateModelIdDTO certificateModelId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetLatestCertificateExternalTypeVersionResponseBuilder {

  }
}
