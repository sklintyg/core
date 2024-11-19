package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.AnswerToMessageEntityMapper;

@ExtendWith(MockitoExtension.class)
class MessageRelationAnswerTest {

  private static final MessageId MESSAGE_ID = new MessageId("messageId");
  @Mock
  private MessageRelationEntityRepository messageRelationEntityRepository;

  @Mock
  private MessageEntityRepository messageEntityRepository;

  @Mock
  private AnswerToMessageEntityMapper answerToMessageEntityMapper;

  @InjectMocks
  private MessageRelationAnswer messageRelationAnswer;

  private final Message message = mock(Message.class);
  private final MessageEntity messageEntity = mock(MessageEntity.class);
  private final MessageEntity savedAnswer = mock(MessageEntity.class);
  private final Answer answer = mock(Answer.class);

  @Test
  void shallReturnIfAnswerIsNull() {
    when(message.answer()).thenReturn(null);

    messageRelationAnswer.save(message, messageEntity);

    verify(messageEntityRepository, never()).save(any());
    verify(messageRelationEntityRepository, never()).save(any());
  }

  @Test
  void shallDeleteAnswerIfStatusDeletedDraft() {
    final var answerId = "expectedId";
    when(message.answer()).thenReturn(
        Answer.builder()
            .id(new MessageId(answerId))
            .status(MessageStatus.DELETED_DRAFT)
            .build()
    );

    final var answerEntity = MessageEntity.builder().build();
    final var messageEntityRelations = MessageRelationEntity.builder()
        .childMessage(
            MessageEntity.builder()
                .id(answerId)
                .build())
        .build();

    doReturn(Optional.of(answerEntity)).when(messageEntityRepository)
        .findMessageEntityById(answerId);
    doReturn(List.of(messageEntityRelations)).when(messageRelationEntityRepository)
        .findByParentMessage(messageEntity);

    messageRelationAnswer.save(message, messageEntity);

    verify(messageEntityRepository).delete(answerEntity);
    verify(messageRelationEntityRepository).delete(messageEntityRelations);
    verify(messageRelationEntityRepository, never()).save(any());
  }

  @Test
  void shallNotDeleteRelationIfIdDontMatch() {
    final var answerId = "expectedId";
    when(message.answer()).thenReturn(
        Answer.builder()
            .id(new MessageId(answerId))
            .status(MessageStatus.DELETED_DRAFT)
            .build()
    );

    final var answerEntity = MessageEntity.builder().build();
    final var messageEntityRelations = MessageRelationEntity.builder()
        .childMessage(
            MessageEntity.builder()
                .id("wrongId")
                .build())
        .build();

    doReturn(Optional.of(answerEntity)).when(messageEntityRepository)
        .findMessageEntityById(answerId);
    doReturn(List.of(messageEntityRelations)).when(messageRelationEntityRepository)
        .findByParentMessage(messageEntity);

    messageRelationAnswer.save(message, messageEntity);

    verify(messageEntityRepository).delete(answerEntity);
    verify(messageRelationEntityRepository, never()).delete(messageEntityRelations);
    verify(messageRelationEntityRepository, never()).save(any());
  }

  @Test
  void shallNotCreateNewRelationWhenAnswerExistsButMessageRelationsAlreadyContainsAnswer() {
    when(message.answer()).thenReturn(answer);
    when(messageRelationEntityRepository.findByParentMessage(messageEntity)).thenReturn(
        List.of(
            MessageRelationEntity.builder()
                .messageRelationType(
                    MessageRelationTypeEntity.builder()
                        .type(MessageRelationType.ANSWER.name())
                        .build()
                )
                .build()
        ));
    when(answer.id()).thenReturn(MESSAGE_ID);

    messageRelationAnswer.save(message, messageEntity);

    verify(messageEntityRepository, times(0)).save(savedAnswer);
    verify(messageRelationEntityRepository, times(0)).save(any(MessageRelationEntity.class));
  }

  @Test
  void shallSaveMessageRelationEntityIfNoPreviousAnswerIsPresent() {
    when(messageEntityRepository.save(any(MessageEntity.class))).thenReturn(savedAnswer);
    when(answerToMessageEntityMapper.toEntity(any(MessageEntity.class),
        any(Answer.class), any())).thenReturn(savedAnswer);
    when(message.answer()).thenReturn(answer);
    when(messageRelationEntityRepository.findByParentMessage(messageEntity)).thenReturn(
        List.of(
            MessageRelationEntity.builder()
                .messageRelationType(
                    MessageRelationTypeEntity.builder()
                        .type(MessageRelationType.REMINDER.name())
                        .build()
                )
                .build()
        ));
    when(answer.id()).thenReturn(MESSAGE_ID);

    messageRelationAnswer.save(message, messageEntity);

    verify(messageEntityRepository, times(1)).save(savedAnswer);
    verify(messageRelationEntityRepository, times(1)).save(any(MessageRelationEntity.class));
  }
}
