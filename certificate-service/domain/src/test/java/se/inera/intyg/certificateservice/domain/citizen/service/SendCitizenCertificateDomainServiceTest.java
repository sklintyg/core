package se.inera.intyg.certificateservice.domain.citizen.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.common.model.PersonIdType.COORDINATION_NUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.exception.CitizenCertificateForbidden;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class SendCitizenCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private SendCitizenCertificateDomainService sendCitizenCertificateDomainService;

  private static final PersonId PERSON_ID = PersonId.builder()
      .id(ATHENA_REACT_ANDERSSON_ID)
      .type(COORDINATION_NUMBER)
      .build();

  @Test
  void shallThrowExceptionIfCertificateNotIssuedOnPatient() {
    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);

    assertThrows(CitizenCertificateForbidden.class,
        () -> sendCitizenCertificateDomainService.send(CERTIFICATE_ID, PERSON_ID)
    );
  }

  @Test
  void shallThrowExceptionIfCertificateNotAvailableForCitizen() {
    final var certificate = mock(MedicalCertificate.class);
    final var certificateModel = CertificateModel.builder()
        .availableForCitizen(false)
        .build();

    doReturn(true).when(certificate).isCertificateIssuedOnPatient(PERSON_ID);
    doReturn(certificateModel).when(certificate).certificateModel();
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);

    assertThrows(CitizenCertificateForbidden.class,
        () -> sendCitizenCertificateDomainService.send(CERTIFICATE_ID, PERSON_ID)
    );
  }

  @Test
  void shallSendCertificate() {
    final var certificateModel = CertificateModel.builder()
        .availableForCitizen(true)
        .build();
    final var certificate = mock(MedicalCertificate.class);

    doReturn(true).when(certificate).isCertificateIssuedOnPatient(PERSON_ID);
    doReturn(certificateModel).when(certificate).certificateModel();
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);

    sendCitizenCertificateDomainService.send(CERTIFICATE_ID, PERSON_ID);

    verify(certificate).sendByCitizen();
  }

  @Test
  void shallReturnSentCertificate() {
    final var expectedCertificate = mock(MedicalCertificate.class);
    final var certificateModel = CertificateModel.builder()
        .availableForCitizen(true)
        .build();
    final var certificate = mock(MedicalCertificate.class);

    doReturn(true).when(certificate).isCertificateIssuedOnPatient(PERSON_ID);
    doReturn(certificateModel).when(certificate).certificateModel();
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    final var actualCertificate = sendCitizenCertificateDomainService.send(CERTIFICATE_ID,
        PERSON_ID);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shallPublishSentCertificateEvent() {
    final var expectedCertificate = mock(MedicalCertificate.class);

    final var certificateModel = CertificateModel.builder()
        .availableForCitizen(true)
        .build();
    final var certificate = mock(MedicalCertificate.class);

    doReturn(true).when(certificate).isCertificateIssuedOnPatient(PERSON_ID);
    doReturn(certificateModel).when(certificate).certificateModel();
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
    doReturn(expectedCertificate).when(certificateRepository).save(certificate);

    sendCitizenCertificateDomainService.send(CERTIFICATE_ID, PERSON_ID);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.SENT, certificateEventCaptor.getValue().type()),
        () -> assertEquals(expectedCertificate, certificateEventCaptor.getValue().certificate()),
        () -> assertNull(certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }
}