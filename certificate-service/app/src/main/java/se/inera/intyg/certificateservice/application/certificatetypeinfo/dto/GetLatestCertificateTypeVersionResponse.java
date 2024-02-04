package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse.GetLatestCertificateTypeVersionResponseBuilder;

@JsonDeserialize(builder = GetLatestCertificateTypeVersionResponseBuilder.class)
@Value
@Builder
public class GetLatestCertificateTypeVersionResponse {

  CertificateModelIdDTO certificateModelId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetLatestCertificateTypeVersionResponseBuilder {

  }
}
