package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.DELETE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.REVISION;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class DeleteCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private DeleteCertificateDomainService deleteCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToUpdate() {
    final var revision = new Revision(1);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(DELETE, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> deleteCertificateDomainService.delete(CERTIFICATE_ID, revision, ACTION_EVALUATION)
    );
  }

  @Test
  void shallDeleteCertificate() {
    final var revision = new Revision(1);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(DELETE, Optional.of(ACTION_EVALUATION));

    deleteCertificateDomainService.delete(CERTIFICATE_ID, revision, ACTION_EVALUATION);

    verify(certificate).delete(revision, ACTION_EVALUATION);
  }

  @Test
  void shallUpdateMetaData() {
    final var revision = new Revision(1);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(DELETE, Optional.of(ACTION_EVALUATION));

    deleteCertificateDomainService.delete(CERTIFICATE_ID, revision, ACTION_EVALUATION);

    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallReturnDeletedCertificate() {
    final var revision = new Revision(1);
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(DELETE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = deleteCertificateDomainService.delete(CERTIFICATE_ID, revision,
        ACTION_EVALUATION);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishDeleteCertificateEvent() {
    final var revision = new Revision(1);
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(DELETE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    deleteCertificateDomainService.delete(CERTIFICATE_ID, revision, ACTION_EVALUATION);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.DELETED, certificateEventCaptor.getValue().type()),
        () -> assertEquals(expectedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var certificate = mock(MedicalCertificate.class);
    final var expectedReason = List.of("expectedReason");
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(DELETE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(DELETE, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> deleteCertificateDomainService.delete(CERTIFICATE_ID, REVISION, ACTION_EVALUATION)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}