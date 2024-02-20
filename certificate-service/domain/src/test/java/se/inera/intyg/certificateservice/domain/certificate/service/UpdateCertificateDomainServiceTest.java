package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.UPDATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.REVISION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.DATE;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class UpdateCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;

  @InjectMocks
  private UpdateCertificateDomainService updateCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToUpdate() {
    final var data = List.of(DATE);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(UPDATE, ACTION_EVALUATION);

    assertThrows(CertificateActionForbidden.class,
        () -> updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION,
            REVISION)
    );
  }

  @Test
  void shallUpdateMetadataInCertificate() {
    final var data = List.of(DATE);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, ACTION_EVALUATION);

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0));

    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallUpdateDataInCertificate() {
    final var data = List.of(DATE);
    final var revision = new Revision(0);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, ACTION_EVALUATION);

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0));

    verify(certificate).updateData(data, revision, ACTION_EVALUATION);
  }

  @Test
  void shallSaveCertificate() {
    final var data = List.of(DATE);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, ACTION_EVALUATION);

    updateCertificateDomainService.update(CERTIFICATE_ID, data, ACTION_EVALUATION, new Revision(0));

    verify(certificateRepository).save(certificate);
  }

  @Test
  void shallReturnSaveCertificate() {
    final var expectedCertificate = Certificate.builder().build();
    final var data = List.of(DATE);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(UPDATE, ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = updateCertificateDomainService.update(CERTIFICATE_ID, data,
        ACTION_EVALUATION, new Revision(0));

    assertEquals(expectedCertificate, actualCertificate);
  }
}