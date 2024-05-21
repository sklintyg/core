package se.inera.intyg.certificateservice.application.message.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO.QuestionDTOBuilder;

@JsonDeserialize(builder = QuestionDTOBuilder.class)
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
  List<ResourceLinkDTO> links;

  @JsonPOJOBuilder(withPrefix = "")
  public static class QuestionDTOBuilder {

  }
}
