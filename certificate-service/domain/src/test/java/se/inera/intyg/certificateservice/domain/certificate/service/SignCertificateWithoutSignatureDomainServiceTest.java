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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.actionEvaluationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class SignCertificateWithoutSignatureDomainServiceTest {

  private static final Revision REVISION = new Revision(1);
  public static final Xml XML = new Xml("XML");

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @Mock
  private XmlGenerator xmlGenerator;
  @InjectMocks
  private SignCertificateWithoutSignatureDomainService signCertificateDomainService;
  private ActionEvaluation actionEvaluation;

  @BeforeEach
  void setUp() {
    actionEvaluation = actionEvaluationBuilder()
        .user(
            ajlaDoctorBuilder()
                .role(Role.PRIVATE_DOCTOR)
                .build()
        )
        .build();
  }

  @Test
  void shallThrowExceptionIfUserHasNoAccessToUpdate() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(false).when(certificate).allowTo(SIGN, actionEvaluation);

    assertThrows(CertificateActionForbidden.class,
        () -> signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, actionEvaluation)
    );
  }

  @Test
  void shallSignCertificate() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, actionEvaluation);

    signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, actionEvaluation);

    verify(certificate).sign(xmlGenerator, REVISION, actionEvaluation
    );
  }

  @Test
  void shallUpdateMetaData() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, actionEvaluation);

    signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, actionEvaluation);

    verify(certificate).updateMetadata(actionEvaluation);
  }

  @Test
  void shallReturnSignedCertificate() {
    final var expectedCertificate = mock(Certificate.class);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, actionEvaluation);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = signCertificateDomainService.sign(CERTIFICATE_ID, REVISION,
        actionEvaluation);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishSignCertificateEvent() {
    final var expectedCertificate = mock(Certificate.class);

    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, actionEvaluation);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, actionEvaluation);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.SIGNED, certificateEventCaptor.getValue().type()),
        () -> assertEquals(expectedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(actionEvaluation, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }

  @Test
  void shallThrowCertificateActionForbiddenIfRoleIsNotPrivateDoctor() {
    final var certificate = mock(Certificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(true).when(certificate).allowTo(SIGN, ACTION_EVALUATION);

    assertThrows(CertificateActionForbidden.class,
        () -> signCertificateDomainService.sign(CERTIFICATE_ID, REVISION, ACTION_EVALUATION)
    );
  }
}