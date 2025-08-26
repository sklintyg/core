package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class GetCertificateInternalServiceTest {

  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CertificateConverter certificateConverter;
  @InjectMocks
  GetCertificateInternalService getCertificateInternalService;

  @Test
  void shallReturnGetCertificateResponse() {
    final var certificate = MedicalCertificate.builder().build();
    final var certificateDto = CertificateDTO.builder().build();
    final var expectedResponse = GetCertificateInternalResponse.builder()
        .certificate(certificateDto)
        .build();

    doReturn(certificate).when(certificateRepository).getById(new CertificateId(CERTIFICATE_ID));
    doReturn(certificateDto).when(certificateConverter)
        .convert(certificate, Collections.emptyList(), null);
    final var actualResponse = getCertificateInternalService.get(CERTIFICATE_ID);
    assertEquals(expectedResponse, actualResponse);
  }
}