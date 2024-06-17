package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.unit.dto.MessagesQueryCriteriaDTO.MessagesQueryCriteriaDTOBuilder;

@JsonDeserialize(builder = MessagesQueryCriteriaDTOBuilder.class)
@Value
@Builder
public class MessagesQueryCriteriaDTO {

  List<String> issuedOnUnitIds;
  Boolean forwarded;
  QuestionSenderTypeDTO senderType;
  LocalDateTime sentDateFrom;
  LocalDateTime sentDateTo;
  String issuedByStaffId;
  PersonIdDTO patientId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class MessagesQueryCriteriaDTOBuilder {

  }
}
