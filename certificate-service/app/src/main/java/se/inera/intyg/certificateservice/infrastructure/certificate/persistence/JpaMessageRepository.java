package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@Repository
public class JpaMessageRepository implements MessageRepository {

  @Override
  public Message save(Message message) {
    return null;
  }
}
