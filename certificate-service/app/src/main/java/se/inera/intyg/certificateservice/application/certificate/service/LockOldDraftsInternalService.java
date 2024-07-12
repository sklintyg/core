package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.LockOldDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.SetCertificatesToLockedDomainService;

@Service
@RequiredArgsConstructor
public class LockOldDraftsInternalService {

  private final CertificateRepository certificateRepository;
  private final SetCertificatesToLockedDomainService setCertificatesToLockedDomainService;
  private final CertificateConverter converter;

  @Transactional
  public LockOldDraftsResponse lock(LockOldDraftsRequest request) {
    if (request.getCutoffDate() == null) {
      throw new IllegalArgumentException("Cutoff date cannot be null");
    }

    final var certificates = certificateRepository.draftsCreatedBefore(request.getCutoffDate());
    setCertificatesToLockedDomainService.lock(certificates);

    return LockOldDraftsResponse.builder()
        .certificates(
            certificates.stream()
                .map(draft -> converter.convert(draft, Collections.emptyList()))
                .toList()
        )
        .build();
  }
}
