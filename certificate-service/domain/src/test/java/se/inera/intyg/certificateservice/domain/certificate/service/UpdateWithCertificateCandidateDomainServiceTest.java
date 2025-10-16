package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.READ;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class UpdateWithCertificateCandidateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final CertificateId CANDIDATE_CERTIFICATE_ID = new CertificateId(
      "candidateCertificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  private static final ExternalReference EXTERNAL_REFERENCE = new ExternalReference(
      "externalReference");
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @Mock
  private Certificate certificate;
  @Mock
  private Certificate candidateCertificate;
  @InjectMocks
  private UpdateWithCertificateCandidateDomainService updateWithCertificateCandidateDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallValidateIfAllowedToUpdateDraftFromCertificate() {
    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID, CANDIDATE_CERTIFICATE_ID,
        ACTION_EVALUATION, EXTERNAL_REFERENCE);
    verify(certificate).allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
  }

  @Test
  void shallThrowIfNotAllowedToReadCandidateCertificate() {
    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(false).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    assertThrows(CertificateActionForbidden.class,
        () -> updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
            CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE)
    );
  }

  @Test
  void shallThrowIfNotAllowedToUpdateDraftFromCertificate() {
    doReturn(false).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    assertThrows(CertificateActionForbidden.class,
        () -> updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
            CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE)
    );
  }

  @Test
  void shallUpdateCertificateWithCandidateCertificate() {
    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);
    verify(certificate).fillFromCertificate(candidateCertificate);
  }

  @Test
  void shallUpdateCertificateWithExternalReferenceIfNotAlreadyExists() {
    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);
    verify(certificate).externalReference(EXTERNAL_REFERENCE);
  }

  @Test
  void shallNotUpdateCertificateWithExternalReferenceIfAlreadyExists() {
    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(EXTERNAL_REFERENCE).when(certificate).externalReference();
    updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);
    verify(certificate, never()).externalReference(any());
  }

  @Test
  void shallUpdateCertificateWithActionEvaluation() {
    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);
    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallSaveCertificate() {
    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);
    verify(certificateRepository).save(certificate);
  }

  @Test
  void shallReturnSavedCertificate() {
    final var savedCertificate = mock(Certificate.class);

    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(savedCertificate).when(certificateRepository).save(certificate);

    final var actual = updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);

    assertEquals(savedCertificate, actual);
  }

  @Test
  void shallPublishUpdateWithCertificateCandidateEvent() {
    final var savedCertificate = mock(Certificate.class);

    doReturn(true).when(certificate)
        .allowTo(UPDATE_DRAFT_FROM_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(candidateCertificate).when(certificateRepository).getById(CANDIDATE_CERTIFICATE_ID);
    doReturn(true).when(candidateCertificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(savedCertificate).when(certificateRepository).save(certificate);

    updateWithCertificateCandidateDomainService.update(CERTIFICATE_ID,
        CANDIDATE_CERTIFICATE_ID, ACTION_EVALUATION, EXTERNAL_REFERENCE);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.UPDATE_WITH_CERTIFICATE_CANDIDATE,
            certificateEventCaptor.getValue().type()),
        () -> assertEquals(savedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }
}
