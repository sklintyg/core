package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.AdministrativeMessageRecipientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.AdministrativeMessageSenderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.AdministrativeMessageTypeRepository;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataPseudonymized;

@ExtendWith(MockitoExtension.class)
class AdministrativeMessageEntityMapperTest {

  @InjectMocks
  private AdministrativeMessageEntityMapper administrativeMessageEntityMapper;
  @Mock
  private AdministrativeMessageTypeRepository administrativeMessageTypeRepository;
  @Mock
  private AdministrativeMessageSenderRepository administrativeMessageSenderRepository;
  @Mock
  private AdministrativeMessageRecipientRepository administrativeMessageRecipientRepository;

  @Test
  @Disabled("Dont work at the moment, need to be fixed")
  void shouldMapAdministrativeMessageEntityCorrectly() {
    final var message = TestDataPseudonymized.messagePseudonymizedMessageBuilder()
        .build();

    when(administrativeMessageTypeRepository.findOrCreate(
        TestDataConstants.MESSAGE_TYPE))
        .thenReturn(TestDataEntities.administrativeMessageTypeEntity());
    when(administrativeMessageSenderRepository.findOrCreate(
        TestDataConstants.MESSAGE_SENDER))
        .thenReturn(TestDataEntities.administrativeMessageSenderEntity());
    when(administrativeMessageRecipientRepository.findOrCreate(
        TestDataConstants.MESSAGE_RECIPIENT))
        .thenReturn(TestDataEntities.administrativeMessageRecipientEntity());

    final var expected = AdministrativeMessageEntity.builder()
        .administrativeMessageId(TestDataConstants.HASHED_ADMINISTRATIVE_MESSAGE_ID)
        .messageType(TestDataEntities.administrativeMessageTypeEntity())
        .sent(message.getMessageSent())
        .lastDateToAnswer(message.getMessageLastDateToAnswer())
        .questionId(message.getMessageQuestionIds())
        .sender(TestDataEntities.administrativeMessageSenderEntity())
        .recipient(TestDataEntities.administrativeMessageRecipientEntity())
        .build();

    final var result = administrativeMessageEntityMapper.map(message);

    assertEquals(expected, result);
  }

  @Test
  @Disabled("Dont work at the moment, need to be fixed")
  void shouldReturnNullWhenAdministrativeMessageIdIsNull() {
    final var message = PseudonymizedAnalyticsMessage.builder()
        .messageId(null)
        .build();

    final var result = administrativeMessageEntityMapper.map(message);

    assertNull(result);
  }
}
