package se.inera.intyg.cts.infrastructure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendNotification;
import se.inera.intyg.cts.infrastructure.integration.SendEmail;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;

@Service
public class SendPackageNotification implements SendNotification {

  private static final Logger LOG = LoggerFactory.getLogger(SendPackageNotification.class);

  @Value("${message.notification.html}")
  private String notificationHtmlMessage;

  @Value("${message.notification.text}")
  private String notificationTextMessage;

  @Value("${message.reminder.html}")
  private String reminderHtmlMessage;

  @Value("${message.reminder.text}")
  private String reminderTextMessage;

  @Value("${message.notification.subject}")
  private String notificationSubject;

  @Value("${message.reminder.subject}")
  private String reminderSubject;

  private static final String SMS = "sms";
  private static final String EMAIL = "email";
  private static final String REMINDER = "reminder";
  private static final String NOTIFICATION = "notification";

  private final SendSMS sendSMS;
  private final SendEmail sendEmail;
  private final MessageFormatter messageFormatter;
  private final TerminationRepository terminationRepository;

  public SendPackageNotification(SendSMS sendSMS, SendEmail sendEmail,
      MessageFormatter messageFormatter, TerminationRepository terminationRepository) {
    this.sendSMS = sendSMS;
    this.sendEmail = sendEmail;
    this.messageFormatter = messageFormatter;
    this.terminationRepository = terminationRepository;
  }

  @Override
  @Transactional
  public void sendNotification(Termination termination) {
    final var smsSuccess = sendSms(notificationTextMessage, NOTIFICATION, termination);
    final var emailSuccess = sendEmail(notificationHtmlMessage, NOTIFICATION, notificationSubject,
        termination);

    if (smsSuccess || emailSuccess) {
      updateStatus(termination.terminationId(), NOTIFICATION);
    }
  }

  @Override
  @Transactional
  public void sendReminder(Termination termination) {
    final var smsSuccess = sendSms(reminderTextMessage, REMINDER, termination);
    final var emailSuccess = sendEmail(reminderHtmlMessage, REMINDER, reminderSubject, termination);

    if (smsSuccess || emailSuccess) {
      updateStatus(termination.terminationId(), REMINDER);
    }
  }

  private boolean sendSms(String message, String statusType, Termination termination) {
    try {
      final var phoneNumber = termination.export().organizationRepresentative().phoneNumber().number();
      final var formattedPhoneNumber = messageFormatter.formatPhoneNumber(phoneNumber);
      final var smsResponseDTO = sendSMS.sendSMS(formattedPhoneNumber, message);
      logSendMessageSuccess(SMS, statusType, termination.terminationId(), smsResponseDTO.job_id(),
          smsResponseDTO.log_href());
      return true;

    } catch (Exception e) {
      logSendMessageFailure(SMS, statusType, termination.terminationId(), e);
      return false;
    }
  }

  private boolean sendEmail(String message, String statusType, String subject, Termination termination) {
    try {
      final var emailAddress = termination.export().organizationRepresentative().emailAddress().emailAddress();
      final var formattedEmailAddress = messageFormatter.formatEmailAddress(emailAddress);
      final var emailResponseDTO = sendEmail.sendEmail(formattedEmailAddress, message, subject);
      logSendMessageSuccess(EMAIL, statusType, termination.terminationId(), emailResponseDTO.job_id(),
          emailResponseDTO.log_href());
      return true;

    } catch (Exception e) {
      logSendMessageFailure(EMAIL, statusType, termination.terminationId(), e);
      return false;
    }
  }

  private void updateStatus(TerminationId terminationId, String statusType) {
    try {
      final var termination = terminationRepository.findByTerminationId(terminationId)
          .orElseThrow();

      if (statusType.equals(NOTIFICATION)) {
        termination.notificationSent();

      } else if (statusType.equals(REMINDER)) {
        termination.reminderSent();
      }

      terminationRepository.store(termination);

    } catch (Exception e) {
      logTerminationUpdateFailure(terminationId, statusType, e);
    }
  }

  private void logSendMessageSuccess(String messageType, String statusType, TerminationId terminationId,
      String jobId, String logHref) {
    LOG.info("Successfully sent {} {} for {} with jobId '{}' and logHref '{}'.", messageType, statusType,
        terminationId, jobId, logHref);
  }

  private void logSendMessageFailure(String messageType, String statusType, TerminationId terminationId,
      Exception e) {
    LOG.error("Failure sending {} {} for {}.", messageType, statusType, terminationId, e);
  }

  private void logTerminationUpdateFailure(TerminationId terminationId, String type, Exception e) {
    LOG.error("Failure setting status '{} sent' for {}.", type, terminationId, e);
  }
}
