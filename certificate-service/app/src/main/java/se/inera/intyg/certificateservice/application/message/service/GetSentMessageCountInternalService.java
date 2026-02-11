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
      int maxDays) {
    if (Objects.isNull(patientIds) || patientIds.isEmpty()) {
      log.warn("No patient IDs provided for sent message count lookup");
      return GetSentInternalResponse.builder()
          .messages(Map.of())
          .build();
    }

    final var messages = new HashMap<String, UnansweredQAs>();

    final var sanitisedPatientIds = patientIds.stream()
        .map(patientId -> patientId.replace("-", ""))
        .filter(patientId -> {
          if (patientId.isBlank() || patientId.length() != 12) {
            log.warn("Skipping blank or invalid patient ID");
            return false;
          }
          return true;
        })
        .toList();

    try {
      processPatientMessages(sanitisedPatientIds, messages, maxDays);
    } catch (IllegalArgumentException e) {
      log.warn("Failed to process messages for patients {}", e.getMessage());
    }

    return GetSentInternalResponse.builder()
        .messages(messages)
        .build();
  }

  private void processPatientMessages(List<String> patientIds, Map<String, UnansweredQAs> messages,
      int maxDays) {

    final var messageList = messageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
        patientIds, maxDays);

    messageList.forEach(message -> messages.put(
        message.certificateId(),
        UnansweredQAs.builder()
            .complement(message.messageCount())
            .build()));
  }
}
