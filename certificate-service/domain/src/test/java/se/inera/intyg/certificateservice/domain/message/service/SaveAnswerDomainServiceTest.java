package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class SaveAnswerDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final Content CONTENT = new Content("content");
  @Mock
  MessageRepository messageRepository;
  @InjectMocks
  SaveAnswerDomainService saveAnswerDomainService;

  @Test
  void shallThrowIfNotAllowedToSaveAnswer() {
    final var certificate = mock(MedicalCertificate.class);
    final var message = mock(Message.class);

    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.SAVE_ANSWER, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class, () -> saveAnswerDomainService.save(
        message, certificate, ACTION_EVALUATION, CONTENT
    ));
  }

  @Test
  void shallPersistMessage() {
    final var expectedMessage = Message.builder().build();
    final var certificate = mock(MedicalCertificate.class);

    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SAVE_ANSWER, Optional.of(ACTION_EVALUATION));

    saveAnswerDomainService.save(
        expectedMessage, certificate, ACTION_EVALUATION, CONTENT
    );

    verify(messageRepository).save(expectedMessage);
  }

  @Test
  void shallReturnMessageWithAnswer() {
    final var expectedMessage = Message.builder().build();
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SAVE_ANSWER, Optional.of(ACTION_EVALUATION));
    doReturn(expectedMessage).when(messageRepository).save(expectedMessage);

    final var actualMessage = saveAnswerDomainService.save(
        expectedMessage, certificate, ACTION_EVALUATION, CONTENT
    );

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void shallCallSaveAnswerOnMessage() {
    final var message = mock(Message.class);
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.SAVE_ANSWER, Optional.of(ACTION_EVALUATION));
    doReturn(message).when(messageRepository).save(message);

    saveAnswerDomainService.save(
        message, certificate, ACTION_EVALUATION, CONTENT
    );

    verify(message).saveAnswer(AJLA_DOKTOR, CONTENT);
  }
}