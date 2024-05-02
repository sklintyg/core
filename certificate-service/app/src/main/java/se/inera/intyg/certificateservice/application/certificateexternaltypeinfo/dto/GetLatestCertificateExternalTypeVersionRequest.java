package se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionRequest.GetLatestCertificateExternalTypeVersionResponseBuilder;

@JsonDeserialize(builder = GetLatestCertificateExternalTypeVersionResponseBuilder.class)
@Value
@Builder
public class GetLatestCertificateExternalTypeVersionRequest {

  CodeDTO code;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetLatestCertificateExternalTypeVersionResponseBuilder {

  }
}
