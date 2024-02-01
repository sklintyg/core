package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO.CertificateRecipientBuilder;

@JsonDeserialize(builder = CertificateRecipientBuilder.class)
@Value
@Builder
public class CertificateRecipientDTO {

  String id;
  String name;
  LocalDateTime sent;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateRecipientBuilder {

  }
}
