package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.MessageEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntityRepository;

@ExtendWith(MockitoExtension.class)
class JpaMessageRepositoryTest {

  @Mock
  private MessageEntityMapper messageEntityMapper;

  @Mock
  private MessageEntityRepository messageEntityRepository;

  @InjectMocks
  private JpaMessageRepository jpaMessageRepository;

  @Nested
  class TestSaveMessage {

    @Test
    void shallReturnCreatedMessageWhenSaved() {
      final var expectedMessage = complementMessageBuilder().created(LocalDateTime.now()).build();
      final var messageEntityToSave = MessageEntity.builder().build();
      final var savedMessageEntity = MessageEntity.builder().build();

      doReturn(messageEntityToSave).when(messageEntityMapper).toEntity(COMPLEMENT_MESSAGE);
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
}