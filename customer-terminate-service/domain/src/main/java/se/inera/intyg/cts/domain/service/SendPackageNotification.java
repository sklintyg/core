package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class SendPackageNotification {

  private final SendNotification sendNotification;
  private final TerminationRepository terminationRepository;

  public SendPackageNotification(SendNotification sendNotification,
      TerminationRepository terminationRepository) {
    this.sendNotification = sendNotification;
    this.terminationRepository = terminationRepository;
  }

  public void sendNotification(Termination termination) {
    final var sendNotificationSuccess = sendNotification.sendNotification(termination);

    if (sendNotificationSuccess) {
      termination.notificationSent();
      terminationRepository.store(termination);
    }
  }

  public void sendReminder(Termination termination) {
    final var sendReminderSuccess = sendNotification.sendReminder(termination);

    if (sendReminderSuccess) {
      termination.reminderSent();
      terminationRepository.store(termination);
    }
  }
}
