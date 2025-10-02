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
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RecipientEntity;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities;

@ExtendWith(MockitoExtension.class)
class RecipientRepositoryTest {

  @InjectMocks
  private RecipientRepository recipientRepository;
  @Mock
  private RecipientEntityRepository recipientEntityRepository;

  @Test
  void shouldCreateNewRecipientEntityIfNotExists() {
    final var recipient = TestDataEntities.recipientEntity();
    final var savedRecipient = mock(RecipientEntity.class);
    when(recipientEntityRepository.findByRecipient(recipient.getRecipient())).thenReturn(
        Optional.empty());
    when(recipientEntityRepository.save(recipient)).thenReturn(savedRecipient);

    final var result = recipientRepository.findOrCreate(recipient.getRecipient());

    assertEquals(savedRecipient, result);
  }

  @Test
  void shouldFindExistingRecipientEntity() {
    final var recipientName = TestDataEntities.recipientEntity().getRecipient();
    final var entity = mock(RecipientEntity.class);
    when(recipientEntityRepository.findByRecipient(recipientName)).thenReturn(Optional.of(entity));

    final var result = recipientRepository.findOrCreate(recipientName);

    assertEquals(entity, result);
  }

  @Test
  void shouldReturnNullIfRecipientIsNull() {
    assertNull(recipientRepository.findOrCreate(null));
  }

  @Test
  void shouldReturnNullIfRecipientIsEmpty() {
    assertNull(recipientRepository.findOrCreate(" "));
  }
}