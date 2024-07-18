package se.inera.intyg.certificateservice.application.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.patient.CertificatesWithQARequestFactory;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.validator.GetPatientCertificatesWithQARequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesWithQAInternalDomainService;

@ExtendWith(MockitoExtension.class)
class GetPatientCertificatesWithQAInternalServiceTest {

  @Mock
  GetPatientCertificatesWithQARequestValidator requestValidator;
  @Mock
  GetPatientCertificatesWithQAInternalDomainService getPatientCertificatesWithQAInternalDomainService;
  @Mock
  CertificatesWithQARequestFactory certificatesWithQARequestFactory;
  @InjectMocks
  GetPatientCertificatesWithQAInternalService getPatientCertificatesWithQAInternalService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = PatientCertificatesWithQARequest.builder().build();
    doThrow(IllegalArgumentException.class).when(requestValidator).validate(request);

    assertThrows(IllegalArgumentException.class,
        () -> getPatientCertificatesWithQAInternalService.get(request));
  }

  @Test
  void shallBuildCertificatesWithQARequestFromFactory() {
    final var xml = new Xml("xml");
    final var certificatesWithQARequest = CertificatesWithQARequest.builder().build();
    final var request = PatientCertificatesWithQARequest.builder().build();
    final var argumentCaptor = ArgumentCaptor.forClass(PatientCertificatesWithQARequest.class);

    doReturn(certificatesWithQARequest).when(certificatesWithQARequestFactory).create(request);
    doReturn(xml).when(getPatientCertificatesWithQAInternalDomainService)
        .get(certificatesWithQARequest);

    getPatientCertificatesWithQAInternalService.get(request);

    verify(certificatesWithQARequestFactory).create(argumentCaptor.capture());
    assertEquals(request, argumentCaptor.getValue());
  }

  @Test
  void shallReturnGetPatientCertificatesWithQAResponseWithBase64EncodedXml() {
    final var xml = new Xml("xml");
    final var expectedResponse = PatientCertificatesWithQAResponse.builder()
        .list(xml.base64())
        .build();

    final var certificatesWithQARequest = CertificatesWithQARequest.builder().build();
    final var request = PatientCertificatesWithQARequest.builder().build();

    doReturn(certificatesWithQARequest).when(certificatesWithQARequestFactory).create(request);
    doReturn(xml).when(getPatientCertificatesWithQAInternalDomainService)
        .get(certificatesWithQARequest);

    final var actualResponse = getPatientCertificatesWithQAInternalService.get(
        request);
    assertEquals(expectedResponse, actualResponse);
  }
}