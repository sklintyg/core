package se.inera.intyg.cts.application.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {

  void handleReceipt(UUID terminationUUID);

}
