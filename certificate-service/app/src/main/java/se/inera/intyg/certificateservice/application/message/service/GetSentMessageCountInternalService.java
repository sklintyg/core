package se.inera.intyg.certificateservice.application.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.GetSentInternalResponse;
import se.inera.intyg.certificateservice.domain.message.model.UnansweredQAs;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetSentMessageCountInternalService {

  private final MessageRepository messageRepository;

  public GetSentInternalResponse get(List<String> patientIds,
      Integer maxDaysOfUnansweredCommunication) {
    if (Objects.isNull(patientIds) || patientIds.isEmpty()) {
      log.warn("No patient IDs provided for sent message count lookup");
      return GetSentInternalResponse.builder()
          .messages(Map.of())
          .build();
    }

    final var messages = new HashMap<String, UnansweredQAs>();

    final var sanitisedPatientIds = patientIds.stream()
        .filter(patientId -> {
          if (Objects.isNull(patientId) || patientId.isBlank()) {
            log.warn("Skipping null or blank patient ID");
            return false;
          }
          return true;
        })
        .map(patientId -> patientId.replace("-", "")).toList();

    try {
      processPatientMessages(sanitisedPatientIds, messages, maxDaysOfUnansweredCommunication);
    } catch (IllegalArgumentException e) {
      log.warn("Failed to process messages for patients{}", e.getMessage());
    }

    return GetSentInternalResponse.builder()
        .messages(messages)
        .build();
  }

  private void processPatientMessages(List<String> patientIds, Map<String, UnansweredQAs> messages,
      Integer maxDaysOfUnansweredCommunication) {

    final var messageList = messageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
        patientIds, maxDaysOfUnansweredCommunication);

    messageList.forEach(message -> {
      messages.put(
          message.certificateId(),
          UnansweredQAs.builder()
              .complement(message.messageCount())
              .build());
    });
  }
}
