package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetValidSickLeaveCertificateIdsInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetValidSickLeaveCertificateIdsInternalResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetValidSickLeaveCertificatesDomainService;

@Service
@RequiredArgsConstructor
public class GetValidSickLeaveCertificatesInternalService {

  private final GetValidSickLeaveCertificatesDomainService getValidSickLeaveCertificatesDomainService;

  public GetValidSickLeaveCertificateIdsInternalResponse get(
      GetValidSickLeaveCertificateIdsInternalRequest request) {
    if (validateRequest(request)) {
      throw new IllegalArgumentException(
          "Invalid request - Not able to get valid sick leave certificate ids"
      );
    }

    final var sickLeaveCertificates = getValidSickLeaveCertificatesDomainService.get(
        request.getCertificateIds().stream()
            .map(CertificateId::new)
            .toList()
    );

    return GetValidSickLeaveCertificateIdsInternalResponse.builder()
        .certificateIds(
            sickLeaveCertificates.stream()
                .map(Certificate::id)
                .map(CertificateId::id)
                .toList()
        )
        .build();
  }

  private static boolean validateRequest(GetValidSickLeaveCertificateIdsInternalRequest request) {
    return request == null || request.getCertificateIds() == null || request.getCertificateIds()
        .isEmpty();
  }
}