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
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.validator.GetPatientCertificatesWithQARequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesWithQADomainService;

@ExtendWith(MockitoExtension.class)
class GetPatientCertificatesWithQAServiceTest {

  @Mock
  GetPatientCertificatesWithQARequestValidator requestValidator;
  @Mock
  GetPatientCertificatesWithQADomainService getPatientCertificatesWithQADomainService;
  @Mock
  CertificatesWithQARequestFactory certificatesWithQARequestFactory;
  @InjectMocks
  GetPatientCertificatesWithQAService getPatientCertificatesWithQAService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = GetPatientCertificatesWithQARequest.builder().build();
    doThrow(IllegalArgumentException.class).when(requestValidator).validate(request);

    assertThrows(IllegalArgumentException.class,
        () -> getPatientCertificatesWithQAService.get(request));
  }

  @Test
  void shallBuildCertificatesWithQARequestFromFactory() {
    final var xml = new Xml("xml");
    final var certificatesWithQARequest = CertificatesWithQARequest.builder().build();
    final var request = GetPatientCertificatesWithQARequest.builder().build();
    final var argumentCaptor = ArgumentCaptor.forClass(GetPatientCertificatesWithQARequest.class);

    doReturn(certificatesWithQARequest).when(certificatesWithQARequestFactory).create(request);
    doReturn(xml).when(getPatientCertificatesWithQADomainService).get(certificatesWithQARequest);

    getPatientCertificatesWithQAService.get(request);

    verify(certificatesWithQARequestFactory).create(argumentCaptor.capture());
    assertEquals(request, argumentCaptor.getValue());
  }

  @Test
  void shallReturnGetPatientCertificatesWithQAResponseWithBase64EncodedXml() {
    final var xml = new Xml("xml");
    final var expectedResponse = GetPatientCertificatesWithQAResponse.builder()
        .list(xml.base64())
        .build();

    final var certificatesWithQARequest = CertificatesWithQARequest.builder().build();
    final var request = GetPatientCertificatesWithQARequest.builder().build();

    doReturn(certificatesWithQARequest).when(certificatesWithQARequestFactory).create(request);
    doReturn(xml).when(getPatientCertificatesWithQADomainService).get(certificatesWithQARequest);

    final var actualResponse = getPatientCertificatesWithQAService.get(
        request);
    assertEquals(expectedResponse, actualResponse);
  }
}