package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class ReceiptServiceTest {

  private static final UUID TERMINATION_UUID = UUID.randomUUID();

  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private ReceiptService receiptService;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    final var terminationRepository = new JpaTerminationRepository(
        inMemoryTerminationEntityRepository);
    receiptService = new ReceiptService(terminationRepository);
  }

  @Test
  public void shallSetReceiptTime() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity(TERMINATION_UUID));

    receiptService.handleReceipt(TERMINATION_UUID);

    final var terminationEntity = inMemoryTerminationEntityRepository.findByTerminationId(TERMINATION_UUID);
    assertNotNull(terminationEntity.orElseThrow().getExport().getReceiptTime());
  }

  @Test
  public void shallUpdateTerminationStatus() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity(TERMINATION_UUID));

    receiptService.handleReceipt(TERMINATION_UUID);

    final var terminationEntity = inMemoryTerminationEntityRepository.findByTerminationId(TERMINATION_UUID);
    assertEquals(TerminationStatus.RECEIPT_RECEIVED.name(), terminationEntity.orElseThrow().getStatus());
  }

  @Test
  public void shallThrowExceptionIfReceiptForNonExistingTermination() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity(TERMINATION_UUID));

    assertThrows(IllegalStateException.class, () -> receiptService.handleReceipt(UUID.randomUUID()));
  }
}
