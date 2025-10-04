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
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRecipientEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

@ExtendWith(MockitoExtension.class)
class AdministrativeMessageRecipientRepositoryTest {

  @InjectMocks
  private AdministrativeMessageRecipientRepository administrativeMessageRecipientRepository;
  @Mock
  private AdministrativeMessageRecipientEntityRepository administrativeMessageRecipientEntityRepository;

  @Test
  void shouldCreateNewAdministrativeMessageRecipientEntityIfNotExists() {
    final var recipient = TestDataConstants.MESSAGE_RECIPIENT;
    final var expectedEntity = TestDataEntities.administrativeMessageRecipientEntity();
    final var savedEntity = mock(AdministrativeMessageRecipientEntity.class);
    when(administrativeMessageRecipientEntityRepository.findByRecipient(recipient)).thenReturn(
        Optional.empty());
    when(administrativeMessageRecipientEntityRepository.save(expectedEntity)).thenReturn(
        savedEntity);

    final var result = administrativeMessageRecipientRepository.findOrCreate(recipient);

    assertEquals(savedEntity, result);
  }

  @Test
  void shouldFindExistingAdministrativeMessageRecipientEntity() {
    final var recipient = TestDataConstants.MESSAGE_RECIPIENT;
    final var entity = mock(AdministrativeMessageRecipientEntity.class);
    when(administrativeMessageRecipientEntityRepository.findByRecipient(recipient)).thenReturn(
        Optional.of(entity));

    final var result = administrativeMessageRecipientRepository.findOrCreate(recipient);

    assertEquals(entity, result);
  }

  @Test
  void shouldReturnNullIfRecipientIsNull() {
    assertNull(administrativeMessageRecipientRepository.findOrCreate(null));
  }

  @Test
  void shouldReturnNullIfRecipientIsEmpty() {
    assertNull(administrativeMessageRecipientRepository.findOrCreate(" "));
  }
}
