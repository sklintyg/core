package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageInternalResponse.GetCertificateMessageInternalResponseBuilder;

@JsonDeserialize(builder = GetCertificateMessageInternalResponseBuilder.class)
@Value
@Builder
public class GetCertificateMessageInternalResponse {

  List<QuestionDTO> questions;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetCertificateMessageInternalResponseBuilder {

  }
}
