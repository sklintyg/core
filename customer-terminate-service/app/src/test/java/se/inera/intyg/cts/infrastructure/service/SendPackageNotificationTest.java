package se.inera.intyg.cts.infrastructure.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.terminationWithEmailAddress;

import java.util.Optional;
import javax.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.infrastructure.integration.SendEmail;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.TellusTalkResponseDTO;

@ExtendWith(MockitoExtension.class)
class SendPackageNotificationTest {

  @Mock
  private SendEmail sendEmail;
  @Mock
  private SendSMS sendSMS;
  @Mock
  private SmsPhoneNumberFormatter smsPhoneNumberFormatter;
  @Mock
  private TerminationRepository terminationRepository;

  @InjectMocks
  private SendPackageNotification sendPackageNotification;

  private static final String FORMATTED_PHONE = "sms:+467012345678";
  private static final String EMAIL_ADDRESS = "email@address.se";
  private static final String REMINDER_SMS_CONTENT = "reminderSmsContent";
  private static final String REMINDER_EMAIL_CONTENT = "reminderEmailContent";
  private static final String REMINDER_SUBJECT = "reminderSubject";
  private static final String NOTIFICATION_SMS_CONTENT = "notificationSmsContent";
  private static final String NOTIFICATION_EMAIL_CONTENT = "notificationEmailContent";
  private static final String NOTIFICATION_SUBJECT = "notificationSubject";

  private static final Termination TERMINATION = defaultTermination();
  private static final TellusTalkResponseDTO MESSAGE_RESPONSE = new TellusTalkResponseDTO("jobId","logHref");
  private static final Exception BAD_REQUEST = new HttpClientErrorException(HttpStatus.BAD_REQUEST);

  @Nested
  class TestSendNotification {

    @BeforeEach
    public void init() {
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_SMS_CONTENT,
          NOTIFICATION_SMS_CONTENT);
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_EMAIL_CONTENT,
          NOTIFICATION_EMAIL_CONTENT);
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_SUBJECT, NOTIFICATION_SUBJECT);
      doReturn(FORMATTED_PHONE).when(smsPhoneNumberFormatter).formatPhoneNumber(any(String.class));
    }

    @Test
    public void shouldSendNotificationWithSmsAndEmail() throws MessagingException {
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(sendSMS, times(1)).sendSMS(FORMATTED_PHONE, NOTIFICATION_SMS_CONTENT);
      verify(sendEmail, times(1)).sendEmail(EMAIL_ADDRESS, NOTIFICATION_EMAIL_CONTENT,
          NOTIFICATION_SUBJECT);
    }

    @Test
    public void shouldUpdateTerminationStatus() {
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlyEmailFailure() throws MessagingException {
      setSmsMock(MESSAGE_RESPONSE);
      setEmailMockToThrow();
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlySmsFailure() {
      setSmsMock(BAD_REQUEST);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldNotUpdateTerminationStatusWithSmsAndEmailFailure() throws MessagingException {
      setSmsMock(BAD_REQUEST);
      setEmailMockToThrow();

      sendPackageNotification.sendNotification(TERMINATION);

      verifyNoInteractions(terminationRepository);
    }

    @Test
    public void shouldNotThrowIfUpdateTerminationFailure() {
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.empty());

      assertDoesNotThrow(() -> sendPackageNotification.sendNotification(TERMINATION));
    }
  }

  @Nested
  class TestSendReminder {

    @BeforeEach
    public void init() {
      ReflectionTestUtils.setField(sendPackageNotification, REMINDER_SMS_CONTENT,
          REMINDER_SMS_CONTENT);
      ReflectionTestUtils.setField(sendPackageNotification, REMINDER_EMAIL_CONTENT,
          REMINDER_EMAIL_CONTENT);
      ReflectionTestUtils.setField(sendPackageNotification, REMINDER_SUBJECT, REMINDER_SUBJECT);
      doReturn(FORMATTED_PHONE).when(smsPhoneNumberFormatter).formatPhoneNumber(any(String.class));
    }

    @Test
    public void shouldSendNotificationWithSmsAndEmail() throws MessagingException {
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(sendSMS, times(1)).sendSMS(FORMATTED_PHONE, REMINDER_SMS_CONTENT);
      verify(sendEmail, times(1)).sendEmail(EMAIL_ADDRESS, REMINDER_EMAIL_CONTENT,
          REMINDER_SUBJECT);
    }

    @Test
    public void shouldUpdateTerminationStatus() {
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlySmsSuccess() throws MessagingException {
      setSmsMock(MESSAGE_RESPONSE);
      setEmailMockToThrow();
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlyEmailSuccess() {
      setSmsMock(BAD_REQUEST);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldNotUpdateTerminationStatusWithSmsAndEmailFailure() throws MessagingException {
      setSmsMock(BAD_REQUEST);
      setEmailMockToThrow();

      sendPackageNotification.sendReminder(TERMINATION);

      verifyNoInteractions(terminationRepository);
    }

    @Test
    public void shouldNotThrowIfUpdateTerminationFailure() {
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.empty());

      assertDoesNotThrow(() -> sendPackageNotification.sendReminder(TERMINATION));
    }
  }

  @Nested
  class TestEmailAddress {

    @BeforeEach
    public void init() {
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_SMS_CONTENT,
          NOTIFICATION_SMS_CONTENT);
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_EMAIL_CONTENT,
          NOTIFICATION_EMAIL_CONTENT);
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_SUBJECT, NOTIFICATION_SUBJECT);
      doReturn(FORMATTED_PHONE).when(smsPhoneNumberFormatter).formatPhoneNumber(any(String.class));
    }

    @Test
    public void shouldSendNotificationWhenValidAddress1() throws MessagingException {
      final var termination = terminationWithEmailAddress("no-reply.example@address.name.se");
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(termination));

      sendPackageNotification.sendNotification(termination);

      verify(sendEmail, times(1)).sendEmail("no-reply.example@address.name.se", NOTIFICATION_EMAIL_CONTENT,
          NOTIFICATION_SUBJECT);
    }

    @Test
    public void shouldSendNotificationWhenValidAddress2() throws MessagingException {
      final var termination = terminationWithEmailAddress("example@test-name.address.se");
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(termination));

      sendPackageNotification.sendNotification(termination);

      verify(sendEmail, times(1)).sendEmail("example@test-name.address.se", NOTIFICATION_EMAIL_CONTENT,
          NOTIFICATION_SUBJECT);
    }

    @Test
    public void shouldNotSendNotificationEmailWhenInvalidAddress1() {
      final var termination = terminationWithEmailAddress("exam:ple@address.se");
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(termination));

      sendPackageNotification.sendNotification(termination);

      verifyNoInteractions(sendEmail);
    }

    @Test
    public void shouldNotSendNotificationEmailWhenInvalidAddress2() {
      final var termination = terminationWithEmailAddress("example@addressse");
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(termination));

      sendPackageNotification.sendNotification(termination);

      verifyNoInteractions(sendEmail);
    }

    @Test
    public void shouldNotSendNotificationEmailWhenInvalidAddress3() {
      final var termination = terminationWithEmailAddress("exa..mple@address.se");
      setSmsMock(MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(termination));

      sendPackageNotification.sendNotification(termination);

      verifyNoInteractions(sendEmail);
    }
  }

  private <T> void setSmsMock(T smsResponse) {
    if (smsResponse instanceof Exception) {
      doThrow((Exception) smsResponse).when(sendSMS).sendSMS(any(String.class), any(String.class));
    } else {
      doReturn(smsResponse).when(sendSMS).sendSMS(any(String.class), any(String.class));
    }
  }
  private void setEmailMockToThrow() throws MessagingException {
      doThrow(MessagingException.class).when(sendEmail).sendEmail(any(String.class), any(String.class),
          any(String.class));
  }

  private <T> void setTerminationRepoMock(T termination) {
    if (termination instanceof Exception) {
      doThrow((Exception) termination).when(terminationRepository)
          .findByTerminationId(any(TerminationId.class));
    } else {
      doReturn(termination).when(terminationRepository).findByTerminationId(any(TerminationId.class));
    }
  }
}
