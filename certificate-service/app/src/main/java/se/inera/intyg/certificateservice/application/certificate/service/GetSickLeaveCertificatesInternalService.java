package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.SickLeaveConverter;
import se.inera.intyg.certificateservice.application.common.CertificatesRequestFactory;
import se.inera.intyg.certificateservice.domain.certificate.service.GetSickLeaveCertificatesDomainService;

@Service
@RequiredArgsConstructor
public class GetSickLeaveCertificatesInternalService {

  private final GetSickLeaveCertificatesDomainService getSickLeaveCertificatesDomainService;
  private final SickLeaveConverter sickLeaveConverter;

  public GetSickLeaveCertificatesInternalResponse get(
      GetSickLeaveCertificatesInternalRequest request) {
    if (request.getPersonId() == null || request.getPersonId().getId() == null
        || request.getPersonId().getId().isBlank()) {
      throw new IllegalArgumentException("Patient id cannot be null or empty");
    }

    final var sickLeaves = getSickLeaveCertificatesDomainService.get(
        CertificatesRequestFactory.convert(request)
    );

    return GetSickLeaveCertificatesInternalResponse.builder()
        .certificates(
            sickLeaves.stream()
                .map(sickLeaveConverter::toSickLeaveCertificateItem)
                .toList()
        )
        .build();
  }
}

