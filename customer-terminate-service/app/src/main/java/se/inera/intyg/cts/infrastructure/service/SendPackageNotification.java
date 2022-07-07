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

  @Value("${message.notification.email.content}")
  private String notificationEmailContent;

  @Value("${message.notification.sms.content}")
  private String notificationSmsContent;

  @Value("${message.reminder.email.content}")
  private String reminderEmailContent;

  @Value("${message.reminder.sms.content}")
  private String reminderSmsContent;

  @Value("${message.notification.email.subject}")
  private String notificationSubject;

  @Value("${message.reminder.email.subject}")
  private String reminderSubject;

  private static final String SMS = "sms";
  private static final String EMAIL = "email";
  private static final String REMINDER = "reminder";
  private static final String NOTIFICATION = "notification";

  private final SendSMS sendSMS;
  private final SendEmail sendEmail;
  private final SmsPhoneNumberFormatter smsPhoneNumberFormatter;
  private final TerminationRepository terminationRepository;

  public SendPackageNotification(SendSMS sendSMS, SendEmail sendEmail,
      SmsPhoneNumberFormatter smsPhoneNumberFormatter, TerminationRepository terminationRepository) {
    this.sendSMS = sendSMS;
    this.sendEmail = sendEmail;
    this.smsPhoneNumberFormatter = smsPhoneNumberFormatter;
    this.terminationRepository = terminationRepository;
  }

  @Override
  @Transactional
  public void sendNotification(Termination termination) {
    final var smsSuccess = sendSms(notificationSmsContent, NOTIFICATION, termination);
    final var emailSuccess = sendEmail(notificationEmailContent, NOTIFICATION, notificationSubject,
        termination);

    if (smsSuccess || emailSuccess) {
      updateStatus(termination.terminationId(), NOTIFICATION);
    }
  }

  @Override
  @Transactional
  public void sendReminder(Termination termination) {
    final var smsSuccess = sendSms(reminderSmsContent, REMINDER, termination);
    final var emailSuccess = sendEmail(reminderEmailContent, REMINDER, reminderSubject, termination);

    if (smsSuccess || emailSuccess) {
      updateStatus(termination.terminationId(), REMINDER);
    }
  }

  private boolean sendSms(String message, String statusType, Termination termination) {
    try {
      final var phoneNumber = termination.export().organizationRepresentative().phoneNumber().number();
      final var formattedPhoneNumber = smsPhoneNumberFormatter.formatPhoneNumber(phoneNumber);
      final var smsResponseDTO = sendSMS.sendSMS(formattedPhoneNumber, message);
      logSendSmsSuccess(statusType, termination.terminationId(), smsResponseDTO.job_id(),
          smsResponseDTO.log_href());
      return true;

    } catch (Exception e) {
      logSendMessageFailure(SMS, statusType, termination.terminationId(), e);
      return false;
    }
  }

  private boolean sendEmail(String message, String statusType, String subject, Termination termination) {
    try {
      final var emailAddress = termination.export().organizationRepresentative().emailAddress()
          .emailAddress();
      sendEmail.sendEmail(emailAddress, message, subject);
      logSendEmailSuccess(statusType, termination.terminationId());
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

  private void logSendSmsSuccess(String statusType, TerminationId terminationId,
      String jobId, String logHref) {
    LOG.info("Successfully sent sms {} for {} with jobId '{}' and logHref '{}'.", statusType,
        terminationId, jobId, logHref);
  }

  private void logSendEmailSuccess(String statusType, TerminationId terminationId) {
    LOG.info("Successfully sent email {} for {}.", statusType, terminationId);
  }

  private void logSendMessageFailure(String messageType, String statusType, TerminationId terminationId,
      Exception e) {
    LOG.error("Failure sending {} {} for {}.", messageType, statusType, terminationId, e);
  }

  private void logTerminationUpdateFailure(TerminationId terminationId, String type, Exception e) {
    LOG.error("Failure setting status '{} sent' for {}.", type, terminationId, e);
  }
}
