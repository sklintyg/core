package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class ForwardCertificateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  ForwardCertificateDomainService forwardCertificateDomainService;

  @Test
  void shallThrowIfNotAllowedToForwardCertificate() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.FORWARD_CERTIFICATE, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> forwardCertificateDomainService.forward(
            certificate, ACTION_EVALUATION
        ));
  }

  @Test
  void shallForwardIfAllowedToForwardCertificate() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.FORWARD_CERTIFICATE,
            Optional.of(ACTION_EVALUATION));
    forwardCertificateDomainService.forward(certificate, ACTION_EVALUATION);

    verify(certificate).forward();
  }

  @Test
  void shallForwardIfAllowedToForwardCertificateFromList() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.FORWARD_CERTIFICATE,
            Optional.of(ACTION_EVALUATION));
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST,
            Optional.of(ACTION_EVALUATION));
    forwardCertificateDomainService.forward(certificate, ACTION_EVALUATION);

    verify(certificate).forward();
  }

  @Test
  void shallPersistForwardedCertificate() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.FORWARD_CERTIFICATE, Optional.of(ACTION_EVALUATION));

    forwardCertificateDomainService.forward(certificate, ACTION_EVALUATION);

    verify(certificateRepository).save(certificate);
  }

  @Test
  void shallReturnCertificate() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.FORWARD_CERTIFICATE, Optional.of(ACTION_EVALUATION));
    doReturn(certificate).when(certificateRepository).save(certificate);

    final var actualCertificate = forwardCertificateDomainService.forward(certificate,
        ACTION_EVALUATION);

    assertEquals(certificate, actualCertificate);
  }
}