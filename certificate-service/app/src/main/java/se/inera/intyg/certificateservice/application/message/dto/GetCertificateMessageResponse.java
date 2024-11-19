package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse.GetCertificateMessageResponseBuilder;

@JsonDeserialize(builder = GetCertificateMessageResponseBuilder.class)
@Value
@Builder
public class GetCertificateMessageResponse {

  List<QuestionDTO> questions;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateMessageResponseBuilder {

  }
}
