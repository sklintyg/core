package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.XML;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportCertificateInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportInternalResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@ExtendWith(MockitoExtension.class)
class GetCertificateExportsInternalForCareProviderServiceTest {

  private static final int PAGE = 1;
  private static final int SIZE = 10;
  private static final String CARE_PROVIDER_ID = "careProviderId";
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  XmlGenerator xmlGenerator;
  @InjectMocks
  GetCertificateExportsInternalForCareProviderService getCertificateExportsInternalForCareProviderService;

  @Test
  void shallReturnExportInternalResponseExportInternalResponse() {
    final var exportCertificateInternalResponses = List.of(
        ExportCertificateInternalResponse.builder()
            .certificateId(FK7210_CERTIFICATE.id().id())
            .xml(XML.base64())
            .revoked(true)
            .build(),
        ExportCertificateInternalResponse.builder()
            .certificateId(FK3226_CERTIFICATE.id().id())
            .xml(XML.base64())
            .revoked(true)
            .build()
    );

    final var expectedResult = ExportInternalResponse.builder()
        .exports(exportCertificateInternalResponses)
        .build();

    final var certificateExportPage = CertificateExportPage.builder()
        .certificates(List.of(FK7210_CERTIFICATE, FK3226_CERTIFICATE))
        .build();

    final var internalRequest = ExportCertificateInternalRequest.builder()
        .page(PAGE)
        .size(SIZE)
        .build();

    doReturn(certificateExportPage).when(certificateRepository)
        .getExportByCareProviderId(new HsaId(CARE_PROVIDER_ID), PAGE, SIZE);

    final var actualResult = getCertificateExportsInternalForCareProviderService.get(
        internalRequest, CARE_PROVIDER_ID);
    assertEquals(expectedResult, actualResult);
  }
}