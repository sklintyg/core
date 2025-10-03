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
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageIdEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

@ExtendWith(MockitoExtension.class)
class AdministrativeMessageIdRepositoryTest {

  @InjectMocks
  private AdministrativeMessageIdRepository administrativeMessageIdRepository;
  @Mock
  private AdministrativeMessageIdEntityRepository administrativeMessageIdEntityRepository;

  @Test
  void shouldCreateNewAdministrativeMessageIdEntityIfNotExists() {
    final var administrativeMessageId = TestDataConstants.ADMINISTRATIVE_MESSAGE_ID;
    final var expectedEntity = TestDataEntities.administrativeMessageIdEntity();
    final var savedEntity = mock(AdministrativeMessageIdEntity.class);
    when(administrativeMessageIdEntityRepository.findByAdministrativeMessageId(
        administrativeMessageId)).thenReturn(Optional.empty());
    when(administrativeMessageIdEntityRepository.save(expectedEntity)).thenReturn(savedEntity);

    final var result = administrativeMessageIdRepository.findOrCreate(administrativeMessageId);

    assertEquals(savedEntity, result);
  }

  @Test
  void shouldFindExistingAdministrativeMessageIdEntity() {
    final var administrativeMessageId = TestDataConstants.ADMINISTRATIVE_MESSAGE_ID;
    final var entity = mock(AdministrativeMessageIdEntity.class);
    when(administrativeMessageIdEntityRepository.findByAdministrativeMessageId(
        administrativeMessageId)).thenReturn(Optional.of(entity));

    final var result = administrativeMessageIdRepository.findOrCreate(administrativeMessageId);

    assertEquals(entity, result);
  }

  @Test
  void shouldReturnNullIfAdministrativeMessageIdIsNull() {
    assertNull(administrativeMessageIdRepository.findOrCreate(null));
  }

  @Test
  void shouldReturnNullIfAdministrativeMessageIdIsEmpty() {
    assertNull(administrativeMessageIdRepository.findOrCreate(" "));
  }
}
