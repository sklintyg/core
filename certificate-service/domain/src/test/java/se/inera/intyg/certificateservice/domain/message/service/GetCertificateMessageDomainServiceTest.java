package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.READ;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;

@ExtendWith(MockitoExtension.class)
class GetCertificateMessageDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private Certificate certificate;
  @InjectMocks
  private GetCertificateMessageDomainService getCertificateMessageDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallValidateIfAllowedToReadCertificate() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    getCertificateMessageDomainService.get(ACTION_EVALUATION, CERTIFICATE_ID);
    verify(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
  }

  @Test
  void shallThrowIfNotAllowedToRead() {
    doReturn(false).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    assertThrows(CertificateActionForbidden.class,
        () -> getCertificateMessageDomainService.get(ACTION_EVALUATION, CERTIFICATE_ID)
    );
  }

  @Test
  void shallEvaluateAvailableActions() {
    final var message = mock(Message.class);
    final var messages = List.of(message);
    doReturn(messages).when(certificate).messages();
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    getCertificateMessageDomainService.get(ACTION_EVALUATION,
        CERTIFICATE_ID);

    verify(message).evaluateAvailableActions(ACTION_EVALUATION, certificate);
  }

  @Test
  void shallReturnMessages() {
    final var message = mock(Message.class);
    final var expectedMessages = List.of(message);
    doReturn(expectedMessages).when(certificate).messages();
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    final var actualMessages = getCertificateMessageDomainService.get(ACTION_EVALUATION,
        CERTIFICATE_ID);
    assertEquals(expectedMessages, actualMessages);
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var expectedReason = List.of("expectedReason");
    doReturn(false).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(CertificateActionType.READ, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> getCertificateMessageDomainService.get(ACTION_EVALUATION, CERTIFICATE_ID)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}
