package se.inera.intyg.cts.application.task;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import se.inera.intyg.cts.application.service.ExportService;

public class CollectCertificateTextsTask {

  private static final String TASK_NAME = "CollectCertificateTextsTask.run";
  private static final String LOCK_AT_MOST = "PT50S";
  private static final String LOCK_AT_LEAST = "PT50S";

  private final ExportService exportService;

  public CollectCertificateTextsTask(ExportService exportService) {
    this.exportService = exportService;
  }

  @Scheduled(cron = "${task.collectcertificate.cron}")
  @SchedulerLock(name = TASK_NAME, lockAtLeastFor = LOCK_AT_LEAST, lockAtMostFor = LOCK_AT_MOST)
  public void collectCertificates() {
    exportService.collectCertificateTextsToExport();
  }
}
