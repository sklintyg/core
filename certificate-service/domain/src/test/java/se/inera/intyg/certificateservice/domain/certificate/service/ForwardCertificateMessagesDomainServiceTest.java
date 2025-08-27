package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;

import java.util.List;
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
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class ForwardCertificateMessagesDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  @Mock
  MessageRepository messageRepository;
  @InjectMocks
  ForwardCertificateMessagesDomainService forwardCertificateMessagesDomainService;

  @Test
  void shallThrowIfNotAllowedToForwardMessages() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.FORWARD_MESSAGE, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> forwardCertificateMessagesDomainService.forward(
            certificate, ACTION_EVALUATION
        ));
  }

  @Test
  void shallForwardMessages() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.FORWARD_MESSAGE, Optional.of(ACTION_EVALUATION));

    forwardCertificateMessagesDomainService.forward(certificate, ACTION_EVALUATION);

    verify(certificate).forwardMessages();
  }

  @Test
  void shallPersistForwardedMessage() {
    final var expectedMessage = Message.builder().build();
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.FORWARD_MESSAGE, Optional.of(ACTION_EVALUATION));
    doReturn(List.of(expectedMessage)).when(certificate).messages();

    forwardCertificateMessagesDomainService.forward(certificate, ACTION_EVALUATION);

    verify(messageRepository).save(expectedMessage);
  }

  @Test
  void shallReturnCertificate() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.FORWARD_MESSAGE, Optional.of(ACTION_EVALUATION));

    final var actualCertificate = forwardCertificateMessagesDomainService.forward(certificate,
        ACTION_EVALUATION);

    assertEquals(certificate, actualCertificate);
  }
}