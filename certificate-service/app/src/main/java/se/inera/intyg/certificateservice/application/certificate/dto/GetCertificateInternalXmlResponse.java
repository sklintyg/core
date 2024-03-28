package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse.GetCertificateInternalXmlResponseBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

@JsonDeserialize(builder = GetCertificateInternalXmlResponseBuilder.class)
@Value
@Builder
public class GetCertificateInternalXmlResponse {

  String certificateId;
  String certificateType;
  UnitDTO unit;
  UnitDTO careProvider;
  String xml;
  RevokedDTO revoked;
  CertificateRecipientDTO recipient;
  PersonIdDTO patientId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateInternalXmlResponseBuilder {

  }
}
