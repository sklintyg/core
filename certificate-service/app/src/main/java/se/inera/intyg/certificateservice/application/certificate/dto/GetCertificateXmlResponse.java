package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse.GetCertificateXmlResponseBuilder;

@JsonDeserialize(builder = GetCertificateXmlResponseBuilder.class)
@Value
@Builder
public class GetCertificateXmlResponse {

  String certificateId;
  long version;
  String xml;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateXmlResponseBuilder {

  }
}
