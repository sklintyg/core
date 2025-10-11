package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.TotalExportsInternalResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@ExtendWith(MockitoExtension.class)
class GetTotalExportsInternalForCareProviderServiceTest {

  private static final String CARE_PROVIDER_ID = "careProviderId";
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  GetTotalExportsInternalForCareProviderService getTotalExportsInternalForCareProviderService;

  @Test
  void shallReturnTotalExportsInternalResponse() {
    final var expectedResult = TotalExportsInternalResponse.builder()
        .totalCertificates(10)
        .totalRevokedCertificates(2)
        .build();

    final var certificateExportPage = CertificateExportPage.builder()
        .total(10)
        .totalRevoked(2)
        .build();

    doReturn(certificateExportPage).when(certificateRepository)
        .getExportByCareProviderId(new HsaId(CARE_PROVIDER_ID), 0, 1);

    final var actualResponse = getTotalExportsInternalForCareProviderService.get(CARE_PROVIDER_ID);
    assertEquals(expectedResult, actualResponse);
  }
}