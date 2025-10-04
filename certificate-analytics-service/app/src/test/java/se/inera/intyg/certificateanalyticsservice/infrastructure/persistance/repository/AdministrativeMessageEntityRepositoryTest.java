package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.AdministrativeMessageEntityMapper;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataPseudonymized;

@ExtendWith(MockitoExtension.class)
class AdministrativeMessageEntityRepositoryTest {

  @InjectMocks
  private AdministrativeMessageRepository administrativeMessageRepository;
  @Mock
  private AdministrativeMessageEntityRepository administrativeMessageEntityRepository;
  @Mock
  private AdministrativeMessageEntityMapper administrativeMessageEntityMapper;

  @Test
  @Disabled("Dont work at the moment, need to be fixed")
  void shouldCreateNewAdministrativeMessageEntityIfNotExists() {
    final var pseudonymizedMessage = TestDataPseudonymized.administrativeMessagePseudonymizedMessageBuilder()
        .build();
    final var savedAdministrativeMessage = mock(AdministrativeMessageEntity.class);
    when(administrativeMessageEntityRepository.findByAdministrativeMessageId(
        pseudonymizedMessage.getAdministrativeMessageId()))
        .thenReturn(Optional.empty());
    when(administrativeMessageEntityRepository.save(
        org.mockito.ArgumentMatchers.any(AdministrativeMessageEntity.class)))
        .thenReturn(savedAdministrativeMessage);

    final var result = administrativeMessageRepository.findOrCreate(pseudonymizedMessage);

    assertEquals(savedAdministrativeMessage, result);
  }

  @Test
  void shouldFindExistingAdministrativeMessageEntity() {
    final var pseudonymizedMessage = TestDataPseudonymized.administrativeMessagePseudonymizedMessageBuilder()
        .build();
    final var entity = mock(AdministrativeMessageEntity.class);
    when(administrativeMessageEntityRepository.findByAdministrativeMessageId(
        pseudonymizedMessage.getAdministrativeMessageId()))
        .thenReturn(Optional.of(entity));

    final var result = administrativeMessageRepository.findOrCreate(pseudonymizedMessage);

    assertEquals(entity, result);
  }

  @Test
  void shouldReturnNullIfAdministrativeMessageIdIsNull() {
    final var pseudonymizedMessage = TestDataPseudonymized.administrativeMessagePseudonymizedMessageBuilder()
        .administrativeMessageId(null)
        .build();

    assertNull(administrativeMessageRepository.findOrCreate(pseudonymizedMessage));
  }

  @Test
  void shouldReturnNullIfAdministrativeMessageIdIsEmpty() {
    final var pseudonymizedMessage = TestDataPseudonymized.administrativeMessagePseudonymizedMessageBuilder()
        .administrativeMessageId(" ")
        .build();

    assertNull(administrativeMessageRepository.findOrCreate(pseudonymizedMessage));
  }
}
