package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.SIGN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class SignCertificateDomainServiceTest {

  private static final Revision REVISION = new Revision(1);
  private static final Signature SIGNATURE = new Signature("signature");
  public static final Xml XML = new Xml("XML");

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @Mock
  private XmlGenerator xmlGenerator;
  @InjectMocks
  private SignCertificateDomainService signCertificateDomainService;

  @Test
  void shallThrowExceptionIfUserHasNoAccessToUpdate() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(SIGN, ACTION_EVALUATION);

    assertThrows(CertificateActionForbidden.class,
        () -> signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, SIGNATURE,
            ACTION_EVALUATION)
    );
  }

  @Test
  void shallSignCertificate() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, ACTION_EVALUATION);

    signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, SIGNATURE, ACTION_EVALUATION);

    verify(certificate).sign(xmlGenerator, SIGNATURE, REVISION, ACTION_EVALUATION);
  }

  @Test
  void shallUpdateMetaData() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, ACTION_EVALUATION);

    signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, SIGNATURE, ACTION_EVALUATION);

    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallReturnSignedCertificate() {
    final var expectedCertificate = mock(Certificate.class);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = signCertificateDomainService.sign(CERTIFICATE_ID, REVISION,
        SIGNATURE, ACTION_EVALUATION);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishSignCertificateEvent() {
    final var expectedCertificate = mock(Certificate.class);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, ACTION_EVALUATION);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, SIGNATURE, ACTION_EVALUATION);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.SIGNED, certificateEventCaptor.getValue().type()),
        () -> assertEquals(expectedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }
}