package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesResponse.GetUnitMessagesResponseBuilder;

@JsonDeserialize(builder = GetUnitMessagesResponseBuilder.class)
@Value
@Builder
public class GetUnitMessagesResponse {

  List<QuestionDTO> questions;
  List<CertificateDTO> certificates;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetUnitMessagesResponseBuilder {

  }
}
