package se.inera.intyg.certificateservice.domain.message.repository;

import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;

public interface MessageRepository {

  Message save(Message message);

  boolean exists(MessageId messageId);

}
