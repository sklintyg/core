package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

@Repository
@RequiredArgsConstructor
public class MessageRelationRepository {

  private final List<MessageRelation> messageRelations;

  public void save(Message message, MessageEntity messageEntity) {
    messageRelations.forEach(relation -> relation.save(message, messageEntity));
  }
}
