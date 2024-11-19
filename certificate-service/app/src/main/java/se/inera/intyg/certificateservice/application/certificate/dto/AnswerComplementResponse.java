package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementResponse.AnswerComplementResponseBuilder;

@JsonDeserialize(builder = AnswerComplementResponseBuilder.class)
@Value
@Builder
public class AnswerComplementResponse {

  CertificateDTO certificate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AnswerComplementResponseBuilder {

  }
}
