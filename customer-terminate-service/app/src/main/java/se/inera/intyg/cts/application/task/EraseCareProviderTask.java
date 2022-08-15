package se.inera.intyg.cts.application.task;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.inera.intyg.cts.application.service.EraseService;
import se.inera.intyg.cts.application.service.EraseServiceImpl;

@Component
public class EraseCareProviderTask {

  private static final String TASK_NAME = "EraseCareProviderTask.run";
  private static final String LOCK_AT_MOST = "PT50S";
  private static final String LOCK_AT_LEAST = "PT50S";

  private final EraseService eraseService;

  public EraseCareProviderTask(EraseServiceImpl eraseService) {
    this.eraseService = eraseService;
  }

  @Scheduled(cron = "${task.erasecareprovider.cron}")
  @SchedulerLock(name = TASK_NAME, lockAtLeastFor = LOCK_AT_LEAST, lockAtMostFor = LOCK_AT_MOST)
  public void eraseCareProvider() {
    eraseService.erase();
  }
}
