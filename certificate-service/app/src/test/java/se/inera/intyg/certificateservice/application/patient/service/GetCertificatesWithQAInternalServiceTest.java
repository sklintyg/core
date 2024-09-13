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
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQARequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQAResponse;
import se.inera.intyg.certificateservice.application.certificate.service.CertificatesWithQARequestFactory;
import se.inera.intyg.certificateservice.application.patient.service.validator.CertificatesWithQARequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.patient.service.GetCertificatesWithQAInternalDomainService;

@ExtendWith(MockitoExtension.class)
class GetCertificatesWithQAInternalServiceTest {

  @Mock
  CertificatesWithQARequestValidator requestValidator;
  @Mock
  GetCertificatesWithQAInternalDomainService getCertificatesWithQAInternalDomainService;
  @Mock
  CertificatesWithQARequestFactory certificatesWithQARequestFactory;
  @InjectMocks
  GetCertificatesWithQAInternalService getCertificatesWithQAInternalService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = CertificatesInternalWithQARequest.builder().build();
    doThrow(IllegalArgumentException.class).when(requestValidator).validate(request);

    assertThrows(IllegalArgumentException.class,
        () -> getCertificatesWithQAInternalService.get(request));
  }

  @Test
  void shallBuildCertificatesWithQARequestFromFactory() {
    final var xml = new Xml("xml");
    final var certificatesWithQARequest = se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest.builder()
        .build();
    final var request = CertificatesInternalWithQARequest.builder().build();
    final var argumentCaptor = ArgumentCaptor.forClass(CertificatesInternalWithQARequest.class);

    doReturn(certificatesWithQARequest).when(certificatesWithQARequestFactory).create(request);
    doReturn(xml).when(getCertificatesWithQAInternalDomainService)
        .get(certificatesWithQARequest);

    getCertificatesWithQAInternalService.get(request);

    verify(certificatesWithQARequestFactory).create(argumentCaptor.capture());
    assertEquals(request, argumentCaptor.getValue());
  }

  @Test
  void shallReturnCertificatesWithQAResponseWithBase64EncodedXml() {
    final var xml = new Xml("xml");
    final var expectedResponse = CertificatesInternalWithQAResponse.builder()
        .list(xml.base64())
        .build();

    final var certificatesWithQARequest = se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest.builder()
        .build();
    final var request = CertificatesInternalWithQARequest.builder().build();

    doReturn(certificatesWithQARequest).when(certificatesWithQARequestFactory).create(request);
    doReturn(xml).when(getCertificatesWithQAInternalDomainService)
        .get(certificatesWithQARequest);

    final var actualResponse = getCertificatesWithQAInternalService.get(
        request);
    assertEquals(expectedResponse, actualResponse);
  }
}
