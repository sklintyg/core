package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities.messageTypeEntity;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageTypeRepositoryTest {

  @Mock
  private MessageTypeEntityRepository messageTypeEntityRepository;
  @InjectMocks
  private MessageTypeRepository messageTypeRepository;

  @Test
  void shallReturnExistingMessageTypeIfAlreadyExists() {
    final var expected = messageTypeEntity();

    when(messageTypeEntityRepository.findByType(MESSAGE_TYPE)).thenReturn(Optional.of(expected));

    final var actual = messageTypeRepository.findOrCreate(MESSAGE_TYPE);
    assertEquals(expected, actual);
  }

  @Test
  void shallReturnNewMessageTypeIfNotExists() {
    final var expected = messageTypeEntity();

    when(messageTypeEntityRepository.findByType(MESSAGE_TYPE)).thenReturn(Optional.empty());
    when(messageTypeEntityRepository.save(expected)).thenReturn(expected);
    
    final var actual = messageTypeRepository.findOrCreate(MESSAGE_TYPE);
    assertEquals(expected, actual);
  }
}