package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.AdministrativeMessageIdRepository;
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
  private AdministrativeMessageIdRepository administrativeMessageIdRepository;
  @Mock
  private AdministrativeMessageTypeRepository administrativeMessageTypeRepository;
  @Mock
  private AdministrativeMessageSenderRepository administrativeMessageSenderRepository;
  @Mock
  private AdministrativeMessageRecipientRepository administrativeMessageRecipientRepository;

  @Test
  void shouldMapAdministrativeMessageEntityCorrectly() {
    final var message = TestDataPseudonymized.administrativeMessagePseudonymizedMessageBuilder()
        .build();

    when(
        administrativeMessageIdRepository.findOrCreate(TestDataConstants.ADMINISTRATIVE_MESSAGE_ID))
        .thenReturn(TestDataEntities.administrativeMessageIdEntity());
    when(administrativeMessageTypeRepository.findOrCreate(
        TestDataConstants.ADMINISTRATIVE_MESSAGE_TYPE))
        .thenReturn(TestDataEntities.administrativeMessageTypeEntity());
    when(administrativeMessageSenderRepository.findOrCreate(
        TestDataConstants.ADMINISTRATIVE_MESSAGE_SENDER))
        .thenReturn(TestDataEntities.administrativeMessageSenderEntity());
    when(administrativeMessageRecipientRepository.findOrCreate(
        TestDataConstants.ADMINISTRATIVE_MESSAGE_RECIPIENT))
        .thenReturn(TestDataEntities.administrativeMessageRecipientEntity());

    final var expected = AdministrativeMessageEntity.builder()
        .administrativeMessageId(TestDataEntities.administrativeMessageIdEntity())
        .answerId(TestDataConstants.ADMINISTRATIVE_MESSAGE_ANSWER_ID)
        .reminderId(TestDataConstants.ADMINISTRATIVE_MESSAGE_REMINDER_ID)
        .messageType(TestDataEntities.administrativeMessageTypeEntity())
        .sent(message.getAdministrativeMessageSent())
        .lastDateToAnswer(message.getAdministrativeMessageLastDateToAnswer())
        .questionId(message.getAdministrativeMessageQuestionId())
        .sender(TestDataEntities.administrativeMessageSenderEntity())
        .recipient(TestDataEntities.administrativeMessageRecipientEntity())
        .build();

    final var result = administrativeMessageEntityMapper.map(message);

    assertEquals(expected, result.get());
  }

  @Test
  void shouldReturnEmptyOptionalWhenAdministrativeMessageIdIsNull() {
    final var message = PseudonymizedAnalyticsMessage.builder()
        .administrativeMessageId(null)
        .build();

    final var result = administrativeMessageEntityMapper.map(message);

    assertTrue(result.isEmpty());
  }
}
