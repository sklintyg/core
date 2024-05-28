package se.inera.intyg.certificateservice.application.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageExistsService {

  private final MessageRepository messageRepository;

  public MessageExistsResponse exist(String messageId) {
    if (messageId == null || messageId.isBlank()) {
      throw new IllegalArgumentException(
          "Required parameter messageId missing: '%s'".formatted(messageId)
      );
    }
    return MessageExistsResponse.builder()
        .exists(
            messageRepository.exists(new MessageId(messageId))
        )
        .build();
  }
}
