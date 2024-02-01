package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReceiverDTO.CertificateReceiverBuilder;

@JsonDeserialize(builder = CertificateReceiverBuilder.class)
@Value
@Builder
public class CertificateReceiverDTO {

  String name;
  boolean approved;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateReceiverBuilder {

  }
}
