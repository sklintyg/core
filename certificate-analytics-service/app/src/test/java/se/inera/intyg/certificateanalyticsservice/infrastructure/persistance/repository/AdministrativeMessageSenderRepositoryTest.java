package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageSenderEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

@ExtendWith(MockitoExtension.class)
class AdministrativeMessageSenderRepositoryTest {

  @InjectMocks
  private AdministrativeMessageSenderRepository administrativeMessageSenderRepository;
  @Mock
  private AdministrativeMessageSenderEntityRepository administrativeMessageSenderEntityRepository;

  @Test
  void shouldCreateNewAdministrativeMessageSenderEntityIfNotExists() {
    final var sender = TestDataConstants.MESSAGE_SENDER;
    final var expectedEntity = TestDataEntities.administrativeMessageSenderEntity();
    final var savedEntity = mock(AdministrativeMessageSenderEntity.class);
    when(administrativeMessageSenderEntityRepository.findBySender(sender)).thenReturn(
        Optional.empty());
    when(administrativeMessageSenderEntityRepository.save(expectedEntity)).thenReturn(savedEntity);

    final var result = administrativeMessageSenderRepository.findOrCreate(sender);

    assertEquals(savedEntity, result);
  }

  @Test
  void shouldFindExistingAdministrativeMessageSenderEntity() {
    final var sender = TestDataConstants.MESSAGE_SENDER;
    final var entity = mock(AdministrativeMessageSenderEntity.class);
    when(administrativeMessageSenderEntityRepository.findBySender(sender)).thenReturn(
        Optional.of(entity));

    final var result = administrativeMessageSenderRepository.findOrCreate(sender);

    assertEquals(entity, result);
  }

  @Test
  void shouldReturnNullIfSenderIsNull() {
    assertNull(administrativeMessageSenderRepository.findOrCreate(null));
  }

  @Test
  void shouldReturnNullIfSenderIsEmpty() {
    assertNull(administrativeMessageSenderRepository.findOrCreate(" "));
  }
}
