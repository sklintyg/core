package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.UPDATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REFERENCE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.REVISION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.DATE;

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
class UpdateCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private UpdateCertificateDomainService updateCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToUpdate() {
    final var data = List.of(DATE);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION,
            REVISION, null)
    );
  }

  @Test
  void shallUpdateMetadataInCertificate() {
    final var data = List.of(DATE);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0),
        null);

    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallUpdateExternalReferenceIfExternalReferenceIsNotAlreadySet() {
    final var data = List.of(DATE);
    final var certificate = mock(MedicalCertificate.class);

    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0),
        EXTERNAL_REFERENCE);

    verify(certificate).externalReference(EXTERNAL_REFERENCE);
  }

  @Test
  void shallNotUpdateExternalReferenceIfExternalReferenceIsAlreadySet() {
    final var data = List.of(DATE);
    final var certificate = mock(MedicalCertificate.class);

    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));

    certificate.externalReference(EXTERNAL_REFERENCE);

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0),
        EXTERNAL_REFERENCE);

    verify(certificate, never()).externalReference(null);
  }

  @Test
  void shallUpdateDataInCertificate() {
    final var data = List.of(DATE);
    final var revision = new Revision(0);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0),
        null);

    verify(certificate).updateData(data, revision, ACTION_EVALUATION);
  }

  @Test
  void shallSaveCertificate() {
    final var data = List.of(DATE);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0),
        null);

    verify(certificateRepository).save(certificate);
  }

  @Test
  void shallReturnSaveCertificate() {
    final var expectedCertificate = MedicalCertificate.builder().build();
    final var data = List.of(DATE);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = updateCertificateDomainService.update(CERTIFICATE_ID, data,
        ACTION_EVALUATION, new Revision(0), null);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishUpdateCertificateEvent() {
    final var expectedCertificate = MedicalCertificate.builder().build();
    final var data = List.of(DATE);

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0),
        null);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.UPDATED, certificateEventCaptor.getValue().type()),
        () -> assertEquals(expectedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var data = List.of(DATE);
    final var certificate = mock(MedicalCertificate.class);
    final var expectedReason = List.of("expectedReason");
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(UPDATE, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION,
            new Revision(0), null)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}