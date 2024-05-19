package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO.QuestionBuilder;

@JsonDeserialize(builder = QuestionBuilder.class)
@Value
@Builder
public class QuestionDTO {

  String id;
  QuestionTypeDTO type;
  String subject;
  String message;
  String author;
  LocalDateTime sent;
  ComplementDTO[] complementDTOS;
  boolean isHandled;
  boolean isForwarded;
  AnswerDTO answerDTO;
  CertificateRelationDTO answeredByCertificate;
  ReminderDTO[] reminders;
  LocalDateTime lastUpdate;
  LocalDate lastDateToReply;
  String[] contactInfo;
  String certificateId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class QuestionBuilder {

  }
}
