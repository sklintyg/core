package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.LockDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.service.LockCertificateDomainService;

@Service
@RequiredArgsConstructor
public class LockDraftsInternalService {

  private final LockCertificateDomainService lockCertificateDomainService;
  private final CertificateConverter converter;

  @Transactional
  public LockDraftsResponse lock(LockDraftsRequest request) {
    if (request.getCutoffDate() == null) {
      throw new IllegalArgumentException("Cutoff date cannot be null");
    }

    final var lockedCertificates = lockCertificateDomainService.lock(request.getCutoffDate());

    return LockDraftsResponse.builder()
        .certificates(
            lockedCertificates.stream()
                .map(draft ->
                    converter.convert(draft, Collections.emptyList(), null)
                )
                .toList()
        )
        .build();
  }
}
