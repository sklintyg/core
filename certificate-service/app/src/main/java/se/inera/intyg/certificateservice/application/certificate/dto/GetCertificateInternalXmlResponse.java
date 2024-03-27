package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse.GetCertificateInternalXmlResponseBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;

@JsonDeserialize(builder = GetCertificateInternalXmlResponseBuilder.class)
@Value
@Builder
public class GetCertificateInternalXmlResponse {

  String certificateId;
  String certificateType;
  String unitId;
  String xml;
  Revoked revoked;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateInternalXmlResponseBuilder {

  }
}
