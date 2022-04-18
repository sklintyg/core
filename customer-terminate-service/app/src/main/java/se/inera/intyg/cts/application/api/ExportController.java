package se.inera.intyg.cts.application.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.cts.application.service.ExportService;

@RestController
@RequestMapping("/api/v1/exports")
public class ExportController {

  private final ExportService exportService;

  public ExportController(ExportService exportService) {
    this.exportService = exportService;
  }

  @PostMapping("/collectCertificates")
  void startCollectCertificates() {
    exportService.collectCertificatesToExport();
  }
}
