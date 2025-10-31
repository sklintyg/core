package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificateInternalRequest.GetSickLeaveCertificateInternalRequestBuilder;

@JsonDeserialize(builder = GetSickLeaveCertificateInternalRequestBuilder.class)
@Value
@Builder
public class GetSickLeaveCertificateInternalRequest {

  @Builder.Default
  boolean ignoreModelRules = false;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetSickLeaveCertificateInternalRequestBuilder {

  }

}
