package se.inera.intyg.certificateservice.domain.message.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class SetMessagesToHandleDomainService {

  private final MessageRepository messageRepository;

  public void handle(List<Message> messages) {
    messages.forEach(Message::handle);
    messages.forEach(messageRepository::save);
  }
}
