package se.inera.intyg.cts.application.task;

import org.springframework.scheduling.annotation.Scheduled;
import se.inera.intyg.cts.application.service.ExportService;

public class CollectCertificatesTask {

  private final ExportService exportService;

  public CollectCertificatesTask(ExportService exportService) {
    this.exportService = exportService;
  }

  @Scheduled(fixedRate = 9999999)
  public void collectCertificates() {
    exportService.collectCertificatesToExport();
  }
}
