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
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageTypeEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

@ExtendWith(MockitoExtension.class)
class AdministrativeMessageTypeRepositoryTest {

  @InjectMocks
  private AdministrativeMessageTypeRepository administrativeMessageTypeRepository;
  @Mock
  private AdministrativeMessageTypeEntityRepository administrativeMessageTypeEntityRepository;

  @Test
  void shouldCreateNewAdministrativeMessageTypeEntityIfNotExists() {
    final var type = TestDataConstants.ADMINISTRATIVE_MESSAGE_TYPE;
    final var expectedEntity = TestDataEntities.administrativeMessageTypeEntity();
    final var savedEntity = mock(AdministrativeMessageTypeEntity.class);
    when(administrativeMessageTypeEntityRepository.findByType(type)).thenReturn(Optional.empty());
    when(administrativeMessageTypeEntityRepository.save(expectedEntity)).thenReturn(savedEntity);

    final var result = administrativeMessageTypeRepository.findOrCreate(type);

    assertEquals(savedEntity, result);
  }

  @Test
  void shouldFindExistingAdministrativeMessageTypeEntity() {
    final var type = TestDataConstants.ADMINISTRATIVE_MESSAGE_TYPE;
    final var entity = mock(AdministrativeMessageTypeEntity.class);
    when(administrativeMessageTypeEntityRepository.findByType(type)).thenReturn(
        Optional.of(entity));

    final var result = administrativeMessageTypeRepository.findOrCreate(type);

    assertEquals(entity, result);
  }

  @Test
  void shouldReturnNullIfTypeIsNull() {
    assertNull(administrativeMessageTypeRepository.findOrCreate(null));
  }

  @Test
  void shouldReturnNullIfTypeIsEmpty() {
    assertNull(administrativeMessageTypeRepository.findOrCreate(" "));
  }
}
