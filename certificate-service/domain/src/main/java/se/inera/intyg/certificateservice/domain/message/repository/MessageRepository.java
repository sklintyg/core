package se.inera.intyg.certificateservice.domain.message.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.message.model.Message;

public interface MessageRepository {

  Message save(Message message);

  void remove(List<String> messageIds);
}
