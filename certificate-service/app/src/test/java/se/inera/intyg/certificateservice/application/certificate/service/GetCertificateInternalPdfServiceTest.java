package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7211_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalPdfResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.pdfboxgenerator.CertificatePdfGenerator;

@ExtendWith(MockitoExtension.class)
class GetCertificateInternalPdfServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificatePdfGenerator certificatePdfGenerator;
  @InjectMocks
  private GetCertificateInternalPdfService getCertificateInternalPdfService;

  @Test
  void shallReturnResponse() {
    final var expectedResponse = GetCertificateInternalPdfResponse.builder()
        .pdfData("pdf".getBytes())
        .fileName("fileName")
        .build();

    final var pdf = new Pdf("pdf".getBytes(), "fileName");

    doReturn(FK7211_CERTIFICATE).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));

    doReturn(pdf).when(certificatePdfGenerator)
        .generate(FK7211_CERTIFICATE);

    final var actualResponse = getCertificateInternalPdfService.get(CERTIFICATE_ID);

    assertEquals(expectedResponse, actualResponse);
  }

}