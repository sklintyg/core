package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.TotalExportsInternalResponse;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@Service
@RequiredArgsConstructor
public class GetTotalExportsInternalForCareProviderService {

  private final CertificateRepository certificateRepository;

  public TotalExportsInternalResponse get(String careProviderId) {
    final var certificateExportPage = certificateRepository.getExportByCareProviderId(
        new HsaId(careProviderId),
        0,
        1
    );

    return TotalExportsInternalResponse.builder()
        .totalCertificates(certificateExportPage.total())
        .totalRevokedCertificates(certificateExportPage.totalRevoked())
        .build();
  }
}