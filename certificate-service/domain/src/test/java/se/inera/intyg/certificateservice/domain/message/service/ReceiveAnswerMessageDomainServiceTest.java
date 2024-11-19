package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.ANSWER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
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
class ReceiveAnswerMessageDomainServiceTest {

  private static final MessageId MESSAGE_ID = new MessageId("messageId");
  @Mock
  private Certificate certificate;

  @Mock
  private CertificateRepository certificateRepository;

  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private ReceiveAnswerMessageDomainService receiveAnswerMessageDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallThrowExceptionIfNotAllowedOnCertificate() {
    doReturn(COMPLEMENT_MESSAGE).when(messageRepository).getById(MESSAGE_ID);
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.RECEIVE_ANSWER, Optional.empty());
    doReturn(CERTIFICATE_ID).when(certificate).id();

    assertThrows(CertificateActionForbidden.class,
        () -> receiveAnswerMessageDomainService.receive(MESSAGE_ID, ANSWER)
    );
  }

  @Test
  void shallThrowExceptionIfNotSamePatientAsCertificate() {
    doReturn(COMPLEMENT_MESSAGE).when(messageRepository).getById(MESSAGE_ID);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.RECEIVE_ANSWER, Optional.empty());
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate).isCertificateIssuedOnPatient(ATHENA_REACT_ANDERSSON.id());

    assertThrows(CertificateActionForbidden.class,
        () -> receiveAnswerMessageDomainService.receive(MESSAGE_ID, ANSWER)
    );
  }

  @Nested
  class AnswerMessageAllowed {

    @BeforeEach
    void setUp() {
      doReturn(true).when(certificate)
          .allowTo(CertificateActionType.RECEIVE_ANSWER, Optional.empty());
      doReturn(true).when(certificate).isCertificateIssuedOnPatient(ATHENA_REACT_ANDERSSON.id());
    }

    @Test
    void shallStoreReceivedMessage() {
      final var expectedMessage = complementMessageBuilder().build();

      doReturn(expectedMessage).when(messageRepository).getById(MESSAGE_ID);

      receiveAnswerMessageDomainService.receive(MESSAGE_ID, ANSWER);

      verify(messageRepository).save(expectedMessage);
    }

    @Test
    void shallReturnSavedMessage() {
      final var expectedMessage = complementMessageBuilder().build();

      doReturn(COMPLEMENT_MESSAGE).when(messageRepository).getById(MESSAGE_ID);
      doReturn(expectedMessage).when(messageRepository).save(COMPLEMENT_MESSAGE);

      final var actualMessage = receiveAnswerMessageDomainService.receive(MESSAGE_ID, ANSWER);

      assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shallUpdateAnswer() {
      final var expectedMessage = mock(Message.class);
      doReturn(CERTIFICATE_ID).when(expectedMessage).certificateId();
      doReturn(ATHENA_REACT_ANDERSSON.id()).when(expectedMessage).personId();

      doReturn(expectedMessage).when(messageRepository).getById(MESSAGE_ID);

      receiveAnswerMessageDomainService.receive(MESSAGE_ID, ANSWER);

      verify(expectedMessage).answer(ANSWER);
    }
  }
}
