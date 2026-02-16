package se.inera.intyg.certificateservice.application.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import se.inera.intyg.certificateservice.application.message.dto.GetSentMessagesCountResponse;
import se.inera.intyg.certificateservice.application.message.dto.MessageCount;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetSentMessageCountInternalService {

  private final MessageRepository messageRepository;

  public GetSentMessagesCountResponse get(List<String> patientIds, int maxDays) {
    if (CollectionUtils.isEmpty(patientIds)) {
      log.warn("No patient IDs provided for sent message count lookup");
      return new GetSentMessagesCountResponse(Map.of());
    }

    try {
      return new GetSentMessagesCountResponse(
          processPatientMessages(convertPatientIds(patientIds), maxDays));
    } catch (IllegalArgumentException e) {
      log.warn("Failed to process messages for patients {}", e.getMessage());
      return new GetSentMessagesCountResponse(Map.of());

    }
  }

  private static List<PersonId> convertPatientIds(List<String> patientIds) {
    return patientIds.stream()
        .map(p -> PersonId.builder().id(p).build())
        .toList();
  }

  private Map<String, MessageCount> processPatientMessages(List<PersonId> patientIds,
      int maxDays) {
    final var map = new HashMap<String, MessageCount>();
    final var messageList = messageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
        patientIds, maxDays);

    messageList.forEach(message -> map.put(
        message.certificateId().id(),
        new MessageCount(message.complementsCount(), message.othersCount())
    ));
    return map;
  }
}
