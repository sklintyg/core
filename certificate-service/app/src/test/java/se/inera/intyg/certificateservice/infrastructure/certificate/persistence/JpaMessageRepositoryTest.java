package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.COMPLEMENT_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.MESSAGE_KEY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.MessageEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntitySpecificationFactory;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageRelationEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageRelationRepository;

@ExtendWith(MockitoExtension.class)
class JpaMessageRepositoryTest {

  @Mock
  private MessageRelationEntityRepository messageRelationEntityRepository;

  @Mock
  private MessageRelationRepository messageRelationRepository;

  @Mock
  private MessageEntityMapper messageEntityMapper;

  @Mock
  private MessageEntityRepository messageEntityRepository;

  @Mock
  MessageEntitySpecificationFactory messageEntitySpecificationFactory;

  @InjectMocks
  private JpaMessageRepository jpaMessageRepository;

  @Nested
  class TestSaveMessage {

    @Test
    void shallReturnCreatedMessageWhenSaved() {
      final var expectedMessage = complementMessageBuilder().created(LocalDateTime.now()).build();
      final var messageEntityToSave = MessageEntity.builder().build();
      final var savedMessageEntity = MessageEntity.builder().build();

      doReturn(messageEntityToSave).when(messageEntityMapper).toEntity(COMPLEMENT_MESSAGE, null);
      doReturn(savedMessageEntity).when(messageEntityRepository).save(messageEntityToSave);
      doReturn(expectedMessage).when(messageEntityMapper).toDomain(savedMessageEntity);

      final var actualMessage = jpaMessageRepository.save(COMPLEMENT_MESSAGE);

      assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shallReturnUpdatedMessageWhenSaved() {
      final var expectedMessage = complementMessageBuilder().created(LocalDateTime.now()).build();
      final var existingMessageEntity = MessageEntity.builder()
          .key(MESSAGE_KEY)
          .build();
      final var messageEntityToSave = MessageEntity.builder().build();
      final var savedMessageEntity = MessageEntity.builder().build();

      doReturn(Optional.of(existingMessageEntity)).when(messageEntityRepository)
          .findMessageEntityById(MESSAGE_ID);
      doReturn(messageEntityToSave).when(messageEntityMapper)
          .toEntity(COMPLEMENT_MESSAGE, MESSAGE_KEY);
      doReturn(savedMessageEntity).when(messageEntityRepository).save(messageEntityToSave);
      doReturn(expectedMessage).when(messageEntityMapper).toDomain(savedMessageEntity);

      final var actualMessage = jpaMessageRepository.save(COMPLEMENT_MESSAGE);

      assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shallThrowExceptionIfMessageIsNull() {
      assertThrows(IllegalArgumentException.class, () -> jpaMessageRepository.save(null));
    }

    @Test
    void shallSaveMessageRelations() {
      final var message = complementMessageBuilder().created(LocalDateTime.now()).build();
      final var messageEntityToSave = MessageEntity.builder().build();
      final var savedMessageEntity = MessageEntity.builder().build();

      doReturn(messageEntityToSave).when(messageEntityMapper).toEntity(COMPLEMENT_MESSAGE, null);
      doReturn(savedMessageEntity).when(messageEntityRepository).save(messageEntityToSave);
      doReturn(message).when(messageEntityMapper).toDomain(savedMessageEntity);

      jpaMessageRepository.save(COMPLEMENT_MESSAGE);

      verify(messageRelationRepository).save(COMPLEMENT_MESSAGE, savedMessageEntity);
    }
  }

  @Nested
  class TestRemove {

    @Test
    void shallRemoveMessages() {
      final var ids = List.of("ID1", "ID2");

      jpaMessageRepository.remove(
          ids
      );

      verify(messageEntityRepository).deleteAllByIdIn(ids);
    }

    @Test
    void shallNotRemoveMessagesIfListIsEmpty() {
      jpaMessageRepository.remove(
          Collections.emptyList()
      );

      verifyNoInteractions(messageEntityRepository);
    }

  }

  @Nested
  class TestExists {

    @Test
    void shallReturnTrueIfMessageExists() {
      final var messageId = new MessageId(MESSAGE_ID);
      final var messageEntity = MessageEntity.builder().build();
      doReturn(Optional.of(messageEntity)).when(messageEntityRepository)
          .findMessageEntityById(MESSAGE_ID);
      assertTrue(jpaMessageRepository.exists(messageId));
    }

    @Test
    void shallReturnFalseIfMessageDontExists() {
      final var messageId = new MessageId(MESSAGE_ID);
      doReturn(Optional.empty()).when(messageEntityRepository).findMessageEntityById(MESSAGE_ID);
      assertFalse(jpaMessageRepository.exists(messageId));
    }
  }

  @Nested
  class TestGetById {

    @Test
    void shouldThrowExceptionIfIdIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaMessageRepository.getById(null));
    }

    @Test
    void shouldThrowExceptionIfMessageNotFound() {
      final var id = new MessageId("ID");
      assertThrows(IllegalArgumentException.class,
          () -> jpaMessageRepository.getById(id)
      );
    }

    @Test
    void shouldReturnMessageFromRepository() {
      final var expectedMessage = Message.builder().build();

      when(messageEntityRepository.findMessageEntityById("ID"))
          .thenReturn(Optional.of(COMPLEMENT_MESSAGE_ENTITY));
      when(messageEntityMapper.toDomain(COMPLEMENT_MESSAGE_ENTITY))
          .thenReturn(expectedMessage);

      final var response = jpaMessageRepository.getById(new MessageId("ID"));

      assertEquals(expectedMessage, response);
    }
  }

  @Nested
  class TestDelete {

    @Test
    void shallThrowIfMessageIdIsNull() {
      assertThrows(IllegalArgumentException.class, () -> jpaMessageRepository.deleteById(null));
    }

    @Test
    void shallDeleteMessage() {
      final var messageToBeDeleted = new MessageId(MESSAGE_ID);
      jpaMessageRepository.deleteById(messageToBeDeleted);

      verify(messageEntityRepository).deleteById(messageToBeDeleted.id());
    }
  }

  @Nested
  class TestGetByMessagesRequest {

    @Test
    void shouldReturnMessageFromRepository() {
      final var specification = mock(Specification.class);
      final var expectedMessage = Message.builder().build();
      final var request = MessagesRequest.builder().build();
      when(messageEntitySpecificationFactory.create(request))
          .thenReturn(specification);
      when(messageEntityRepository.findAll(specification))
          .thenReturn(List.of(COMPLEMENT_MESSAGE_ENTITY));
      when(messageEntityMapper.toDomain(COMPLEMENT_MESSAGE_ENTITY))
          .thenReturn(expectedMessage);

      final var response = jpaMessageRepository.findByMessagesRequest(request);

      assertEquals(List.of(expectedMessage), response);
    }

  }
}
