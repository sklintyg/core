package se.inera.intyg.cts.application.api;

import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.cts.application.service.ReceiptService;
import se.inera.intyg.cts.domain.model.TerminationId;

@RestController
@RequestMapping("/api/v1/receipt")
public class ReceiptController {

  private final ReceiptService receiptService;

  public ReceiptController(ReceiptService receiptService) {
    this.receiptService = receiptService;
  }

  @PostMapping("/{terminationUUID}")
  void handleReceipt(@PathVariable UUID terminationUUID) {
    receiptService.handleReceipt(terminationUUID);
  }
}
