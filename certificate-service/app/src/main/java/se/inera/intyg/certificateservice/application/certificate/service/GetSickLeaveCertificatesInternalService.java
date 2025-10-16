package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.SickLeaveConverter;
import se.inera.intyg.certificateservice.domain.certificate.service.GetSickLeaveCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Service
@RequiredArgsConstructor
public class GetSickLeaveCertificatesInternalService {

  private final GetSickLeaveCertificatesDomainService getSickLeaveCertificatesDomainService;
  private final SickLeaveConverter sickLeaveConverter;

  public GetSickLeaveCertificatesInternalResponse get(
      GetSickLeaveCertificatesInternalRequest request) {
    if (request.getPersonId().getId() == null || request.getPersonId().getId().isBlank()) {
      throw new IllegalArgumentException("Patient id cannot be null or empty");
    }

    final var sickLeaves = getSickLeaveCertificatesDomainService.get(
        getRequest(request)
    );

    return GetSickLeaveCertificatesInternalResponse.builder()
        .certificates(
            sickLeaves.stream()
                .map(sickLeaveConverter::convert)
                .toList()
        )
        .build();
  }

  private CertificatesRequest getRequest(GetSickLeaveCertificatesInternalRequest request) {
    return CertificatesRequest.builder()
        .personId(PersonId.builder().id(request.getPersonId().getId()).build())
        .signedFrom(request.getSignedFrom())
        .signedTo(request.getSignedTo())
        .types(
            request.getCertificateTypes().stream()
                .map(CertificateType::new)
                .toList()
        )
        .build();
  }
}

