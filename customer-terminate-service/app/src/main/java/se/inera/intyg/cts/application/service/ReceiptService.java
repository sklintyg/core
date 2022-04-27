package se.inera.intyg.cts.application.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

@Service
public class ReceiptService {
  private static final Logger LOG = LoggerFactory.getLogger(ReceiptService.class);

  private final TerminationRepository terminationRepository;

  public ReceiptService(
      TerminationRepository terminationRepository) {
    this.terminationRepository = terminationRepository;
  }

  @Transactional
  public void handleReceipt(UUID terminationUUID) {
    final var receiptTime = LocalDateTime.now();
    final var terminationId = new TerminationId(terminationUUID);
    final var terminationOptional = terminationRepository.findByTerminationId(terminationId);

    if (terminationOptional.isEmpty()) {
      LOG.error("Received receit from sjut for non-existing terminationId {}.", terminationId);
      throw new IllegalStateException("Received receit from sjut for non-existing terminationId.");
    }

    final var termination = terminationOptional.get();
    termination.receiptReceived(receiptTime);
    terminationRepository.store(termination);
  }
}
