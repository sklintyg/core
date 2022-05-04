package se.inera.intyg.cts.application.task;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.inera.intyg.cts.application.service.ExportService;

@Component
public class ExportTask {

  private static final String TASK_NAME = "CollectCertificateTextsTask.run";
  private static final String LOCK_AT_MOST = "4m";
  private static final String LOCK_AT_LEAST = "4m";

  private final ExportService exportService;

  public ExportTask(ExportService exportService) {
    this.exportService = exportService;
  }

  @Scheduled(cron = "${task.export.cron}")
  @SchedulerLock(name = TASK_NAME, lockAtLeastFor = LOCK_AT_LEAST, lockAtMostFor = LOCK_AT_MOST)
  public void collectCertificates() {
    exportService.export();
  }
}
