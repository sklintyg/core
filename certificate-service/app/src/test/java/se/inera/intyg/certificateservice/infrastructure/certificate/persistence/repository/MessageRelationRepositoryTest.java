package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

@ExtendWith(MockitoExtension.class)
class MessageRelationRepositoryTest {

  @Mock
  MessageRelation messageRelationOne;
  @Mock
  MessageRelation messageRelationTwo;
  MessageRelationRepository messageRelationRepository;

  @BeforeEach
  void setUp() {
    messageRelationRepository = new MessageRelationRepository(
        List.of(messageRelationOne, messageRelationTwo)
    );
  }

  @Test
  void shallCallMultipleMessageRelationsAndSave() {
    final var message = Message.builder().build();
    final var messageEntity = MessageEntity.builder().build();

    messageRelationRepository.save(message, messageEntity);

    verify(messageRelationOne, times(1)).save(message, messageEntity);
    verify(messageRelationTwo, times(1)).save(message, messageEntity);
  }
}
