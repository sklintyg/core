package se.inera.intyg.certificateservice.domain.message.repository;

import se.inera.intyg.certificateservice.domain.message.model.Message;

public interface MessageRepository {

  Message save(Message message);
}
