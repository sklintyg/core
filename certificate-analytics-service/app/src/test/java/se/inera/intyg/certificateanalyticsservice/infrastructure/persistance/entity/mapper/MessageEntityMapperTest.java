package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_MESSAGE_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_RECIPIENT;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_SENDER;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.MESSAGE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities.messageEntity;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities.messageRecipientPartyEntity;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities.messageSenderPartyEntity;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataPseudonymized.messagePseudonymizedMessageBuilder;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.MessageEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.MessageTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PartyRepository;

@ExtendWith(MockitoExtension.class)
class MessageEntityMapperTest {

  @Mock
  private MessageEntityRepository messageEntityRepository;
  @Mock
  private MessageTypeRepository messageTypeRepository;
  @Mock
  private PartyRepository partyRepository;
  @InjectMocks
  private MessageEntityMapper messageEntityMapper;

  @Test
  void shallReturnNullIfMessageIdNull() {
    final var actual = messageEntityMapper.map(messagePseudonymizedMessageBuilder()
        .messageId(null)
        .build()
    );
    assertNull(actual, "Expected null when no message id");
  }

  @Test
  void shallReturnNullIfMessageIdEmpty() {
    final var actual = messageEntityMapper.map(messagePseudonymizedMessageBuilder()
        .messageId("")
        .build()
    );
    assertNull(actual, "Expected null when no message id");
  }

  @Test
  void shallReturnExistingMessageIfAlreadyExists() {
    final var expected = messageEntity().build();

    when(messageEntityRepository.findByMessageId(HASHED_MESSAGE_ID))
        .thenReturn(Optional.of(expected));

    final var actual = messageEntityMapper.map(messagePseudonymizedMessageBuilder().build());
    assertEquals(expected, actual);
  }

  @Test
  void shallReturnNewMessageIfNotExists() {
    final var expected = messageEntity().build();

    when(messageEntityRepository.findByMessageId(HASHED_MESSAGE_ID))
        .thenReturn(Optional.empty());
    when(messageTypeRepository.findOrCreate(MESSAGE_TYPE)).thenReturn(expected.getMessageType());
    when(partyRepository.findOrCreate(MESSAGE_SENDER)).thenReturn(messageSenderPartyEntity());
    when(partyRepository.findOrCreate(MESSAGE_RECIPIENT)).thenReturn(messageRecipientPartyEntity());
    when(messageEntityRepository.save(expected)).thenReturn(expected);

    final var actual = messageEntityMapper.map(messagePseudonymizedMessageBuilder().build());

    assertEquals(expected, actual);
  }
}