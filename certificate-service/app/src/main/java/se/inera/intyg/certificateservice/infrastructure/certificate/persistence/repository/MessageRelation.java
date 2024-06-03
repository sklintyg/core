package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

public interface MessageRelation {

  void save(Message message, MessageEntity messageEntity);
}
