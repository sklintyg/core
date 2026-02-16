package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.ANSWER_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.COMPLEMENT_MESSAGE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataMessageEntity.MESSAGE_KEY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.MessageEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateMessageCountEntity;
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
  private MessageEntitySpecificationFactory messageEntitySpecificationFactory;

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

    @Test
    void shallDeleteMessageIfStatusIsDeletedDraft() {
      final var message = Message.builder()
          .status(MessageStatus.DELETED_DRAFT)
          .id(new MessageId(MESSAGE_ID))
          .build();

      final var messageEntity = MessageEntity.builder().build();
      final var messageEntityToDelete = Optional.of(messageEntity);

      doReturn(messageEntityToDelete).when(messageEntityRepository)
          .findMessageEntityById(MESSAGE_ID);

      jpaMessageRepository.save(message);

      verify(messageEntityRepository).delete(messageEntity);
    }


    @Test
    void shallReturnMessageAfterDeletingEntity() {
      final var message = Message.builder()
          .status(MessageStatus.DELETED_DRAFT)
          .id(new MessageId(MESSAGE_ID))
          .build();

      final var messageEntity = MessageEntity.builder().build();
      final var messageEntityToDelete = Optional.of(messageEntity);

      doReturn(messageEntityToDelete).when(messageEntityRepository)
          .findMessageEntityById(MESSAGE_ID);

      final var actualResult = jpaMessageRepository.save(message);
      assertEquals(message, actualResult);
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
  class TestFindById {

    @Test
    void shouldThrowExceptionIfIdIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaMessageRepository.findById(null));
    }

    @Test
    void shouldThrowExceptionIfMessageNotFound() {
      final var id = new MessageId("ID");
      assertThrows(IllegalArgumentException.class,
          () -> jpaMessageRepository.findById(id)
      );
    }

    @Test
    void shouldReturnParentMessageFromRepository() {
      final var expectedMessage = Message.builder().build();
      final var messageRelationEntity = MessageRelationEntity.builder()
          .parentMessage(COMPLEMENT_MESSAGE_ENTITY)
          .childMessage(ANSWER_MESSAGE_ENTITY)
          .build();

      when(messageEntityRepository.findMessageEntityById("ID"))
          .thenReturn(Optional.of(ANSWER_MESSAGE_ENTITY));
      when(messageRelationEntityRepository.findByChildMessage(ANSWER_MESSAGE_ENTITY))
          .thenReturn(Optional.of(messageRelationEntity));
      when(messageEntityMapper.toDomain(COMPLEMENT_MESSAGE_ENTITY))
          .thenReturn(expectedMessage);

      final var response = jpaMessageRepository.findById(new MessageId("ID"));

      assertEquals(expectedMessage, response);
    }

    @Test
    void shouldReturnMessageFromRepository() {
      final var expectedMessage = Message.builder().build();

      when(messageEntityRepository.findMessageEntityById("ID"))
          .thenReturn(Optional.of(COMPLEMENT_MESSAGE_ENTITY));
      when(messageEntityMapper.toDomain(COMPLEMENT_MESSAGE_ENTITY))
          .thenReturn(expectedMessage);

      final var response = jpaMessageRepository.findById(new MessageId("ID"));

      assertEquals(expectedMessage, response);
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

    @Test
    void shouldReturnEmptyListIfNoMessagesAreFound() {
      final var specification = mock(Specification.class);
      final var request = MessagesRequest.builder().build();
      when(messageEntitySpecificationFactory.create(request))
          .thenReturn(specification);
      when(messageEntityRepository.findAll(specification))
          .thenReturn(Collections.emptyList());

      final var response = jpaMessageRepository.findByMessagesRequest(request);

      assertEquals(Collections.emptyList(), response);
    }

    @Test
    void shouldFilterOutMessagesOfInvalidTypeOrStatus() {
      final var missingMessageEntity = MessageEntity.builder().build();
      final var reminderMessageEntity = MessageEntity.builder().build();
      final var answerMessageEntity = MessageEntity.builder().build();
      final var draftMessageEntity = MessageEntity.builder().build();

      final var missingMessage = Message.builder()
          .type(MessageType.MISSING)
          .build();
      final var reminderMessage = Message.builder()
          .type(MessageType.REMINDER)
          .build();
      final var answerMessage = Message.builder()
          .type(MessageType.ANSWER)
          .build();
      final var draftMessage = Message.builder()
          .type(MessageType.CONTACT)
          .status(MessageStatus.DRAFT)
          .build();

      final var specification = mock(Specification.class);
      final var expectedMessage = Message.builder().build();
      final var request = MessagesRequest.builder().build();

      when(messageEntitySpecificationFactory.create(request))
          .thenReturn(specification);
      when(messageEntityRepository.findAll(specification))
          .thenReturn(
              List.of(COMPLEMENT_MESSAGE_ENTITY, missingMessageEntity, reminderMessageEntity,
                  answerMessageEntity, draftMessageEntity));
      when(messageEntityMapper.toDomain(COMPLEMENT_MESSAGE_ENTITY))
          .thenReturn(expectedMessage);
      when(messageEntityMapper.toDomain(missingMessageEntity))
          .thenReturn(missingMessage);
      when(messageEntityMapper.toDomain(reminderMessageEntity))
          .thenReturn(reminderMessage);
      when(messageEntityMapper.toDomain(answerMessageEntity))
          .thenReturn(answerMessage);
      when(messageEntityMapper.toDomain(draftMessageEntity))
          .thenReturn(draftMessage);

      final var response = jpaMessageRepository.findByMessagesRequest(request);

      assertEquals(List.of(expectedMessage), response);
    }
  }

  @Nested
  class FindMessagesByPatientKeyAndStatusSentAndCreatedAfter {

    private static final String PATIENT_ID = "191212121212";
    private static final Integer MAX_DAYS = 7;

    private static CertificateMessageCountEntity createEntity(String certificateId, int complements,
        int others) {
      return new CertificateMessageCountEntity() {
        @Override
        public String getCertificateId() {
          return certificateId;
        }

        @Override
        public int getComplementsCount() {
          return complements;
        }

        @Override
        public int getOthersCount() {
          return others;
        }
      };
    }

    @Test
    void shallReturnEmptyListWhenNoMessagesFound() {
      when(messageEntityRepository.getMessageCountForCertificates(
          List.of(PATIENT_ID), MAX_DAYS))
          .thenReturn(List.of());

      final var result = jpaMessageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          List.of(PersonId.builder().id(PATIENT_ID).build()), MAX_DAYS);

      assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shallReturnMessageWhenOneMessageFound() {
      final var entity = createEntity("cert123", 5, 1);

      when(messageEntityRepository.getMessageCountForCertificates(
          List.of(PATIENT_ID), MAX_DAYS))
          .thenReturn(List.of(entity));

      final var result = jpaMessageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          List.of(PersonId.builder().id(PATIENT_ID).build()), MAX_DAYS);

      assertEquals(1, result.size());
      assertEquals("cert123", result.getFirst().certificateId().id());
      assertEquals(5, result.getFirst().complementsCount());
      assertEquals(1, result.getFirst().othersCount());
    }

    @Test
    void shallReturnMultipleMessagesWhenMultipleMessagesFound() {
      final var entity1 = createEntity("cert123", 5, 1);
      final var entity2 = createEntity("cert456", 3, 2);

      when(messageEntityRepository.getMessageCountForCertificates(
          List.of(PATIENT_ID), MAX_DAYS))
          .thenReturn(List.of(entity1, entity2));

      final var result = jpaMessageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          List.of(PersonId.builder().id(PATIENT_ID).build()), MAX_DAYS);

      assertEquals(2, result.size());
      assertEquals("cert123", result.getFirst().certificateId().id());
      assertEquals(5, result.getFirst().complementsCount());
      assertEquals(1, result.getFirst().othersCount());
      assertEquals("cert456", result.get(1).certificateId().id());
      assertEquals(3, result.get(1).complementsCount());
      assertEquals(2, result.get(1).othersCount());
    }

    @Test
    void shallCallRepositoryWithCorrectParameters() {
      when(messageEntityRepository.getMessageCountForCertificates(
          List.of(PATIENT_ID), MAX_DAYS))
          .thenReturn(List.of());

      jpaMessageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          List.of(PersonId.builder().id(PATIENT_ID).build()), MAX_DAYS);

      verify(messageEntityRepository).getMessageCountForCertificates(
          List.of(PATIENT_ID), MAX_DAYS);
    }

    @Test
    void shallHandleBatchingWhenPatientIdsLessThanBatchSize() {
      final var patientIds = List.of("1", "2", "3", "4", "5");
      final var entity = createEntity("cert123", 1, 1);

      when(messageEntityRepository.getMessageCountForCertificates(
          patientIds, MAX_DAYS))
          .thenReturn(List.of(entity));

      final var result = jpaMessageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          patientIds.stream().map(p -> PersonId.builder().id(p).build()).toList(), MAX_DAYS);

      assertEquals(1, result.size());
      verify(messageEntityRepository, times(1)).getMessageCountForCertificates(
          patientIds, MAX_DAYS);
    }

    @Test
    void shallHandleBatchingWithMultipleFullBatches() {
      final var patientIds = new ArrayList<String>();
      for (int i = 1; i <= 2001; i++) {
        patientIds.add(String.valueOf(i));
      }

      final var entity1 = createEntity("cert123", 1, 1);
      final var entity2 = createEntity("cert456", 2, 2);
      final var entity3 = createEntity("cert1001", 2, 6);

      when(messageEntityRepository.getMessageCountForCertificates(
          patientIds.subList(0, 1000), MAX_DAYS))
          .thenReturn(List.of(entity1));
      when(messageEntityRepository.getMessageCountForCertificates(
          patientIds.subList(1000, 2000), MAX_DAYS))
          .thenReturn(List.of(entity2));
      when(messageEntityRepository.getMessageCountForCertificates(
          patientIds.subList(2000, 2001), MAX_DAYS))
          .thenReturn(List.of(entity3));

      final var result = jpaMessageRepository.findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          patientIds.stream().map(p -> PersonId.builder().id(p).build()).toList(), MAX_DAYS);

      assertEquals(3, result.size());
      verify(messageEntityRepository, times(1)).getMessageCountForCertificates(
          patientIds.subList(0, 1000), MAX_DAYS);
      verify(messageEntityRepository, times(1)).getMessageCountForCertificates(
          patientIds.subList(1000, 2000), MAX_DAYS);
      verify(messageEntityRepository, times(1)).getMessageCountForCertificates(
          patientIds.subList(2000, 2001), MAX_DAYS);
    }
  }
}
