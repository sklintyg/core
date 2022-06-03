package se.inera.intyg.cts.application.service;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendNotification;
import se.inera.intyg.cts.domain.service.SendPassword;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final TerminationRepository terminationRepository;
    private final SendPassword sendPassword;
    private final SendNotification sendNotification;
    private final Boolean sendPasswordActive;
    private final Boolean sendNotificationsActive;
    private final Integer reminderDelayInDays;

    public MessageServiceImpl(TerminationRepository terminationRepository, SendPassword sendPassword,
        SendNotification sendNotification,
        @Value("${send.password.active}") Boolean sendPasswordActive,
        @Value("${send.notification.active}") Boolean sendNotificationsActive,
        @Value("${send.reminder.after.days}") Integer reminderDelayInDays) {
        this.terminationRepository = terminationRepository;
        this.sendPassword = sendPassword;
        this.sendNotification = sendNotification;
        this.sendPasswordActive = sendPasswordActive;
        this.sendNotificationsActive = sendNotificationsActive;
        this.reminderDelayInDays = reminderDelayInDays;
    }

    @Override
    public void sendPassword() {
        for (Termination termination: terminationRepository
            .findByStatuses(List.of(TerminationStatus.RECEIPT_RECEIVED))) {

            if (sendPasswordActive) {
                try {
                    sendPassword.sendPassword(termination);
                } catch (Exception e) {
                    LOG.error("Failure sending password for termination id {}.",
                        termination.terminationId(), e);
                }

            } else {
                LOG.info("Functionality for sending password is inactive. Not sending password for "
                    + "termination id {}", termination.terminationId());
            }
        }
    }

    @Override
    public void sendNotification() {
        for (final var termination: terminationRepository
            .findByStatuses(List.of(TerminationStatus.EXPORTED))) {

            if (sendNotificationsActive) {
                sendNotification.sendNotification(termination);

            } else {
                LOG.info("Functionality for sending notification is inactive. Not sending "
                    + "notification for termination id {}", termination.terminationId());
            }
        }
    }

    @Override
    public void sendReminder() {
        for (final var termination: terminationRepository
            .findByStatuses(List.of(TerminationStatus.NOTIFICATION_SENT))) {

            if (sendNotificationsActive && isTimeForReminder(termination)) {
                sendNotification.sendReminder(termination);

            } else if (!sendNotificationsActive) {
                LOG.info("Functionality for sending reminder is inactive. Not sending "
                    + "reminder for termination id {}", termination.terminationId());
            }
        }
    }

    private boolean isTimeForReminder(Termination termination) {
        return termination.created().plusDays(reminderDelayInDays).isBefore(LocalDateTime.now());
    }
}
