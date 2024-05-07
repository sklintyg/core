package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateRecipientDTO.CertificateRecipientDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificateRecipientDTOBuilder.class)
public class CertificateRecipientDTO {

  String id;
  String name;
  LocalDateTime sent;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateRecipientDTOBuilder {

  }
}