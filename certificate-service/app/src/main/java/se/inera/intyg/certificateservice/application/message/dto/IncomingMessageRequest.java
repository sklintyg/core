package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest.IncomingMessageRequestBuilder;

@JsonDeserialize(builder = IncomingMessageRequestBuilder.class)
@Value
@Builder
public class IncomingMessageRequest {

  String id;
  String referenceId;
  String certificateId;
  MessageTypeDTO type;
  SentByDTO sentBy;
  LocalDateTime sent;
  PersonIdDTO personId;
  List<String> contactInfo;
  String subject;
  String content;
  String answerMessageId;
  String answerReferenceId;
  String reminderMessageId;
  @Builder.Default
  List<IncomingComplementDTO> complements = Collections.emptyList();
  LocalDate lastDateToAnswer;

  @JsonPOJOBuilder(withPrefix = "")
  public static class IncomingMessageRequestBuilder {

  }
}
