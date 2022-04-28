package se.inera.intyg.cts.application.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

@Service
public class ReceiptService {

  private final TerminationRepository terminationRepository;

  public ReceiptService(
      TerminationRepository terminationRepository) {
    this.terminationRepository = terminationRepository;
  }

  @Transactional
  public void handleReceipt(UUID terminationUUID) {
    final var receiptTime = LocalDateTime.now();
    final var terminationId = new TerminationId(terminationUUID);
    final var terminationOptional =
        terminationRepository.findByTerminationId(terminationId);

    final var termination =
        terminationOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Received receipt for non-existing terminationId '%s'.", terminationId))
    );

    termination.receiptReceived(receiptTime);
    terminationRepository.store(termination);
  }
}
