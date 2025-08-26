package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.RENEW;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REFERENCE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7804_CERTIFICATE_MODEL_ID;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class RenewExternalCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateModelRepository certificateModelRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private RenewExternalCertificateDomainService renewExternalCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToRenew() {
    final var certificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);
    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateModel).placeholderCertificate(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));

    assertThrows(CertificateActionForbidden.class,
        () -> renewExternalCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION,
            EXTERNAL_REFERENCE, FK7804_CERTIFICATE_MODEL_ID)
    );
  }

  @Test
  void shallRenewCertificate() {
    final var certificate = mock(MedicalCertificate.class);
    final var newCertificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateModel).placeholderCertificate(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(certificate).when(certificateRepository).save(certificate);
    doReturn(newCertificate).when(certificate).renew(ACTION_EVALUATION);

    renewExternalCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION,
        EXTERNAL_REFERENCE, FK7804_CERTIFICATE_MODEL_ID);

    verify(certificate).renew(ACTION_EVALUATION);
  }

  @Test
  void shallSetExternalReferenceOnRenewCertificate() {
    final var certificate = mock(MedicalCertificate.class);
    final var newCertificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateModel).placeholderCertificate(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(certificate).when(certificateRepository).save(certificate);
    doReturn(newCertificate).when(certificate).renew(ACTION_EVALUATION);

    renewExternalCertificateDomainService.renew(CERTIFICATE_ID, ACTION_EVALUATION,
        EXTERNAL_REFERENCE, FK7804_CERTIFICATE_MODEL_ID);

    verify(newCertificate).externalReference(EXTERNAL_REFERENCE);
  }

  @Test
  void shallReturnNewCertificate() {
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificate = mock(MedicalCertificate.class);
    final var newCertificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateModel).placeholderCertificate(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(RENEW, Optional.of(ACTION_EVALUATION));
    doReturn(newCertificate).when(certificate).renew(ACTION_EVALUATION);
    doReturn(certificate).when(certificateRepository).save(certificate);
    doReturn(expectedCertificate).when(certificateRepository).save(newCertificate);

    final var actualCertificate = renewExternalCertificateDomainService.renew(CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE, FK7804_CERTIFICATE_MODEL_ID);

    assertEquals(expectedCertificate, actualCertificate);
  }
}