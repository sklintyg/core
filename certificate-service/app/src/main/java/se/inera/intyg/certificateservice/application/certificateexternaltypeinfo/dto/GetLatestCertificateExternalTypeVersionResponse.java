package se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse.GetLatestCertificateExternalTypeVersionResponseBuilder;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;

@JsonDeserialize(builder = GetLatestCertificateExternalTypeVersionResponseBuilder.class)
@Value
@Builder
public class GetLatestCertificateExternalTypeVersionResponse {

  CertificateModelIdDTO certificateModelId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetLatestCertificateExternalTypeVersionResponseBuilder {

  }
}
