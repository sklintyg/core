package se.inera.intyg.certificateanalyticsservice.application.messages.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PseudonymizedAnalyticsMessage {

  String messageId;

  LocalDateTime eventTimestamp;
  String eventMessageType;
  String eventUserId;
  String eventRole;
  String eventUnitId;
  String eventCareProviderId;
  String eventOrigin;
  String eventSessionId;

  String certificateId;
  String certificateType;
  String certificateTypeVersion;
  String certificatePatientId;
  String certificateUnitId;
  String certificateCareProviderId;
  String certificateRelationParentId;
  String certificateRelationParentType;

  String recipientId;

  String administrativeMessageId;
  String administrativeMessageAnswerId;
  String administrativeMessageReminderId;
  String administrativeMessageType;
  LocalDateTime administrativeMessageSent;
  LocalDate administrativeMessageLastDateToAnswer;
  List<String> administrativeMessageQuestionId;
  String administrativeMessageSender;
  String administrativeMessageRecipient;

}