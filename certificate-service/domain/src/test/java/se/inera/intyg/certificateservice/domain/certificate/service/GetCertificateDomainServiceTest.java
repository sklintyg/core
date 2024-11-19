package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.READ;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.UPDATE;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class GetCertificateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @Mock
  private Certificate certificate;
  @InjectMocks
  private GetCertificateDomainService getCertificateDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallValidateIfAllowedToReadCertificate() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
    verify(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
  }

  @Test
  void shallThrowIfNotAllowedToRead() {
    doReturn(false).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    assertThrows(CertificateActionForbidden.class,
        () -> getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
    );
  }

  @Test
  void shallUpdateCertificateMetadataIfDraftAndHasUpdateRights() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));
    doReturn(true).when(certificate).isDraft();
    getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallNotUpdateCertificateMetadataIfDraftAndHasNoUpdateRights() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(false).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));
    doReturn(true).when(certificate).isDraft();
    getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
    verify(certificate, never()).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallNotUpdateCertificateMetadataIfNotDraft() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(false).when(certificate).isDraft();
    getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
    verify(certificate, never()).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallReturnCertificate() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    final var actualCertificate = getCertificateDomainService.get(CERTIFICATE_ID,
        ACTION_EVALUATION);
    assertEquals(certificate, actualCertificate);
  }

  @Test
  void shallPublishGetCertificateEvent() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.READ, certificateEventCaptor.getValue().type()),
        () -> assertEquals(certificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var expectedReason = List.of("expectedReason");
    doReturn(false).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(CertificateActionType.READ, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> getCertificateDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}
