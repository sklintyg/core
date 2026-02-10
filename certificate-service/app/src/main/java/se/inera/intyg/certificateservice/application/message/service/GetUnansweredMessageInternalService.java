package se.inera.intyg.certificateservice.application.message.service;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.GetUnansweredCommunicationInternalResponse;
import se.inera.intyg.certificateservice.domain.message.model.UnansweredQAs;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUnansweredMessageInternalService {

  private final MessageRepository messageRepository;
  private final PatientEntityRepository patientEntityRepository;
  private final CertificateEntityRepository certificateEntityRepository;

  public GetUnansweredCommunicationInternalResponse get(List<String> patientIds,
      Integer maxDaysOfUnansweredCommunication) {
    if (patientIds == null || patientIds.isEmpty()) {
      log.warn("No patient IDs provided for unanswered communication lookup");
      return GetUnansweredCommunicationInternalResponse.builder()
          .messages(Map.of())
          .build();
    }

    final var messages = new HashMap<String, UnansweredQAs>();

    patientIds.stream()
        .filter(patientId -> {
          if (patientId == null || patientId.isBlank()) {
            log.warn("Skipping null or blank patient ID");
            return false;
          }
          return true;
        })
        .map(patientId -> patientId.replace("-", ""))
        .forEach(sanitisedPatientId -> {
          try {
            processPatientMessages(sanitisedPatientId, messages, maxDaysOfUnansweredCommunication);
          } catch (IllegalArgumentException e) {
            log.warn("Failed to process messages for patient {}: {}", sanitisedPatientId,
                e.getMessage());
          }
        });

    return GetUnansweredCommunicationInternalResponse.builder()
        .messages(messages)
        .build();
  }

  private void processPatientMessages(String patientId, Map<String, UnansweredQAs> messages,
      Integer maxDaysOfUnansweredCommunication) {

    final var patientEntity = patientEntityRepository.findById(patientId)
        .orElseThrow(() -> new IllegalArgumentException(
            "Patient with id " + patientId + " does not exist"));

    final var messageList = messageRepository.findMessagesByPatientKeyAndStatusSentAndCreatedAfter(
        patientEntity.getKey(), maxDaysOfUnansweredCommunication);

    messageList.stream()
        .collect(
            groupingBy(message -> message.certificateId().id(),
                counting()
            ))
        .forEach((certificateId, messageCount) -> {
          final var unansweredQAs = UnansweredQAs.builder()
              .complement(messageCount.intValue())
              .build();
          messages.put(certificateId, unansweredQAs);
        });
  }
}
