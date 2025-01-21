package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMessageTypeDTO.CertificateMessageTypeDTOBuilder;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;

@JsonDeserialize(builder = CertificateMessageTypeDTOBuilder.class)
@Value
@Builder
public class CertificateMessageTypeDTO {

  QuestionTypeDTO type;
  String subject;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateMessageTypeDTOBuilder {

  }
}
