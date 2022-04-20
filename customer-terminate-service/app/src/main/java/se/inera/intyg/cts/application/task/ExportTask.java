package se.inera.intyg.cts.application.task;

import org.springframework.scheduling.annotation.Scheduled;
import se.inera.intyg.cts.application.service.ExportService;

public class ExportTask {

  private final ExportService exportService;

  public ExportTask(ExportService exportService) {
    this.exportService = exportService;
  }

  @Scheduled(fixedRate = 10000)
  public void collectCertificates() {
    exportService.export();
  }
}
