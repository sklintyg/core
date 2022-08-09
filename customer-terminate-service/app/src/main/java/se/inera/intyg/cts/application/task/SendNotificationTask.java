package se.inera.intyg.cts.application.task;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.inera.intyg.cts.application.service.MessageService;

@Component
public class SendNotificationTask {

  private static final String TASK_NAME = "SendNotificationTask.run";
  private static final String LOCK_AT_MOST = "PT50S";
  private static final String LOCK_AT_LEAST = "PT50S";

  private final MessageService messageService;

  public SendNotificationTask(MessageService messageService) {
    this.messageService = messageService;
  }

  @Scheduled(cron = "${task.send.notification.cron}")
  @SchedulerLock(name = TASK_NAME, lockAtLeastFor = LOCK_AT_LEAST, lockAtMostFor = LOCK_AT_MOST)
  public void sendNotification() {
    messageService.sendNotification();
  }
}
