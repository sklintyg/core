package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateResponse.SendCitizenCertificateResponseBuilder;

@JsonDeserialize(builder = SendCitizenCertificateResponseBuilder.class)
@Value
@Builder
public class SendCitizenCertificateResponse {

  CertificateDTO citizenCertificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SendCitizenCertificateResponseBuilder {

  }
}