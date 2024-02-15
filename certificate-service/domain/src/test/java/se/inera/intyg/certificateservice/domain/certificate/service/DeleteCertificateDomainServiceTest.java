package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.DELETE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class DeleteCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;

  @InjectMocks
  private DeleteCertificateDomainService deleteCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToUpdate() {
    final var revision = new Revision(1);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(DELETE, ACTION_EVALUATION);

    assertThrows(CertificateActionForbidden.class,
        () -> deleteCertificateDomainService.delete(CERTIFICATE_ID, revision, ACTION_EVALUATION)
    );
  }

  @Test
  void shallDeleteCertificate() {
    final var revision = new Revision(1);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(DELETE, ACTION_EVALUATION);

    deleteCertificateDomainService.delete(CERTIFICATE_ID, revision, ACTION_EVALUATION);

    verify(certificate).delete(revision);
  }

  @Test
  void shallReturnDeletedCertificate() {
    final var revision = new Revision(1);
    final var expectedCertificate = mock(Certificate.class);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(DELETE, ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = deleteCertificateDomainService.delete(CERTIFICATE_ID, revision,
        ACTION_EVALUATION);

    assertEquals(expectedCertificate, actualCertificate);
  }
}