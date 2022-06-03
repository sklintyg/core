package se.inera.intyg.cts.infrastructure.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;

import java.util.Optional;
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
  private MessageFormatter messageFormatter;
  @Mock
  private TerminationRepository terminationRepository;

  @InjectMocks
  private SendPackageNotification sendPackageNotification;

  private static final String PHONE_NUMBER = "070-12345678";
  private static final String FORMATTED_PHONE = "sms:+467012345678";
  private static final String EMAIL_ADDRESS = "email@address.se";
  private static final String FORMATTED_EMAIL = "email:email@address.se";
  private static final String REMINDER_TEXT_MESSAGE = "reminderTextMessage";
  private static final String REMINDER_HTML_MESSAGE = "reminderHtmlMessage";
  private static final String REMINDER_SUBJECT = "reminderSubject";
  private static final String NOTIFICATION_TEXT_MESSAGE = "notificationTextMessage";
  private static final String NOTIFICATION_HTML_MESSAGE = "notificationHtmlMessage";
  private static final String NOTIFICATION_SUBJECT = "notificationSubject";

  private static final Termination TERMINATION = defaultTermination();
  private static final TellusTalkResponseDTO MESSAGE_RESPONSE = new TellusTalkResponseDTO("jobId","logHref");
  private static final Exception BAD_REQUEST = new HttpClientErrorException(HttpStatus.BAD_REQUEST);

  @Nested
  class TestSendNotification {

    @BeforeEach
    public void init() {
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_TEXT_MESSAGE, NOTIFICATION_TEXT_MESSAGE);
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_HTML_MESSAGE, NOTIFICATION_HTML_MESSAGE);
      ReflectionTestUtils.setField(sendPackageNotification, NOTIFICATION_SUBJECT, NOTIFICATION_SUBJECT);
      doReturn(FORMATTED_PHONE).when(messageFormatter).formatPhoneNumber(any(String.class));
      doReturn(FORMATTED_EMAIL).when(messageFormatter).formatEmailAddress(any(String.class));
    }

    @Test
    public void shouldSendNotificationWithSmsAndEmail() {
      setMessageMocks(MESSAGE_RESPONSE, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(sendSMS, times(1)).sendSMS(FORMATTED_PHONE, NOTIFICATION_TEXT_MESSAGE);
      verify(sendEmail, times(1)).sendEmail(FORMATTED_EMAIL, NOTIFICATION_HTML_MESSAGE,
          NOTIFICATION_SUBJECT);
    }

    @Test
    public void shouldUpdateTerminationStatus() {
      setMessageMocks(MESSAGE_RESPONSE, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlyEmailFailure() {
      setMessageMocks(MESSAGE_RESPONSE, BAD_REQUEST);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlySmsFailure() {
      setMessageMocks(BAD_REQUEST, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendNotification(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldNotUpdateTerminationStatusWithSmsAndEmailFailure() {
      setMessageMocks(BAD_REQUEST, BAD_REQUEST);

      sendPackageNotification.sendNotification(TERMINATION);

      verifyNoInteractions(terminationRepository);
    }

    @Test
    public void shouldNotThrowIfUpdateTerminationFailure() {
      setMessageMocks(MESSAGE_RESPONSE, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.empty());

      assertDoesNotThrow(() -> sendPackageNotification.sendNotification(TERMINATION));
    }
  }

  @Nested
  class TestSendReminder {

    @BeforeEach
    public void init() {
      ReflectionTestUtils.setField(sendPackageNotification, REMINDER_TEXT_MESSAGE, REMINDER_TEXT_MESSAGE);
      ReflectionTestUtils.setField(sendPackageNotification, REMINDER_HTML_MESSAGE, REMINDER_HTML_MESSAGE);
      ReflectionTestUtils.setField(sendPackageNotification, REMINDER_SUBJECT, REMINDER_SUBJECT);
      doReturn(FORMATTED_PHONE).when(messageFormatter).formatPhoneNumber(any(String.class));
      doReturn(FORMATTED_EMAIL).when(messageFormatter).formatEmailAddress(any(String.class));
    }

    @Test
    public void shouldSendNotificationWithSmsAndEmail() {
      setMessageMocks(MESSAGE_RESPONSE, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(sendSMS, times(1)).sendSMS(FORMATTED_PHONE, REMINDER_TEXT_MESSAGE);
      verify(sendEmail, times(1)).sendEmail(FORMATTED_EMAIL, REMINDER_HTML_MESSAGE,
          REMINDER_SUBJECT);
    }

    @Test
    public void shouldUpdateTerminationStatus() {
      setMessageMocks(MESSAGE_RESPONSE, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlySmsSuccess() {
      setMessageMocks(MESSAGE_RESPONSE, BAD_REQUEST);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldUpdateTerminationStatusWithOnlyEmailSuccess() {
      setMessageMocks(BAD_REQUEST, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.of(TERMINATION));

      sendPackageNotification.sendReminder(TERMINATION);

      verify(terminationRepository, times(1))
          .findByTerminationId(TERMINATION.terminationId());
      verify(terminationRepository, times(1)).store(TERMINATION);
    }

    @Test
    public void shouldNotUpdateTerminationStatusWithSmsAndEmailFailure() {
      setMessageMocks(BAD_REQUEST, BAD_REQUEST);

      sendPackageNotification.sendReminder(TERMINATION);

      verifyNoInteractions(terminationRepository);
    }

    @Test
    public void shouldNotThrowIfUpdateTerminationFailure() {
      setMessageMocks(MESSAGE_RESPONSE, MESSAGE_RESPONSE);
      setTerminationRepoMock(Optional.empty());

      assertDoesNotThrow(() -> sendPackageNotification.sendReminder(TERMINATION));
    }
  }

  private <T, U> void setMessageMocks(T smsResponse, U emailResponse) {
    if (smsResponse instanceof Exception) {
      doThrow((Exception) smsResponse).when(sendSMS).sendSMS(any(String.class), any(String.class));
    } else {
      doReturn(smsResponse).when(sendSMS).sendSMS(any(String.class), any(String.class));
    }

    if (emailResponse instanceof Exception) {
      doThrow((Exception) emailResponse).when(sendEmail).sendEmail(any(String.class), any(String.class),
          any(String.class));
    } else {
      doReturn(emailResponse).when(sendEmail).sendEmail(any(String.class), any(String.class),
          any(String.class));
    }
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
