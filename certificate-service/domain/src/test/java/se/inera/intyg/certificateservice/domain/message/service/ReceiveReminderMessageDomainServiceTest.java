package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.REMINDER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class ReceiveReminderMessageDomainServiceTest {

  private static final MessageId MESSAGE_ID = new MessageId("messageId");
  @Mock
  private Certificate certificate;

  @Mock
  private CertificateRepository certificateRepository;

  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private ReceiveReminderMessageDomainService receiveReminderMessageDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallThrowExceptionIfNotAllowedOnCertificate() {
    doReturn(COMPLEMENT_MESSAGE).when(messageRepository).getById(MESSAGE_ID);
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.RECEIVE_REMINDER, Optional.empty());
    doReturn(CERTIFICATE_ID).when(certificate).id();

    assertThrows(CertificateActionForbidden.class,
        () -> receiveReminderMessageDomainService.receive(MESSAGE_ID, REMINDER)
    );
  }

  @Test
  void shallThrowExceptionIfNotSamePatientAsCertificate() {
    doReturn(COMPLEMENT_MESSAGE).when(messageRepository).getById(MESSAGE_ID);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.RECEIVE_REMINDER, Optional.empty());
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate).isCertificateIssuedOnPatient(ATHENA_REACT_ANDERSSON.id());

    assertThrows(CertificateActionForbidden.class,
        () -> receiveReminderMessageDomainService.receive(MESSAGE_ID, REMINDER)
    );
  }

  @Nested
  class ReminderMessageAllowed {

    @BeforeEach
    void setUp() {
      doReturn(true).when(certificate)
          .allowTo(CertificateActionType.RECEIVE_REMINDER, Optional.empty());
      doReturn(true).when(certificate).isCertificateIssuedOnPatient(ATHENA_REACT_ANDERSSON.id());
    }

    @Test
    void shallStoreReceivedMessage() {
      final var expectedMessage = complementMessageBuilder().build();

      doReturn(expectedMessage).when(messageRepository).getById(MESSAGE_ID);

      receiveReminderMessageDomainService.receive(MESSAGE_ID, REMINDER);

      verify(messageRepository).save(expectedMessage);
    }

    @Test
    void shallReturnSavedMessage() {
      final var expectedMessage = complementMessageBuilder().build();

      doReturn(COMPLEMENT_MESSAGE).when(messageRepository).getById(MESSAGE_ID);
      doReturn(expectedMessage).when(messageRepository).save(COMPLEMENT_MESSAGE);

      final var actualMessage = receiveReminderMessageDomainService.receive(MESSAGE_ID, REMINDER);

      assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shallUpdateReminders() {
      final var expectedMessage = mock(Message.class);
      doReturn(CERTIFICATE_ID).when(expectedMessage).certificateId();
      doReturn(ATHENA_REACT_ANDERSSON.id()).when(expectedMessage).personId();

      doReturn(expectedMessage).when(messageRepository).getById(MESSAGE_ID);

      receiveReminderMessageDomainService.receive(MESSAGE_ID, REMINDER);

      verify(expectedMessage).remind(REMINDER);
    }
  }
}
