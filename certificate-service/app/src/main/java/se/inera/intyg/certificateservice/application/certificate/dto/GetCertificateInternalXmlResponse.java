package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse.GetCertificateInternalXmlResponseBuilder;

@JsonDeserialize(builder = GetCertificateInternalXmlResponseBuilder.class)
@Value
@Builder
public class GetCertificateInternalXmlResponse {

  String certificateId;
  String certificateType;
  String unitId;
  String xml;
  RevokedDTO revoked;
  CertificateRecipientDTO recipient;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateInternalXmlResponseBuilder {

  }
}
