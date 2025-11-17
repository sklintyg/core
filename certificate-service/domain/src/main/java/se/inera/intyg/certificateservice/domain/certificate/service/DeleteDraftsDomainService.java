package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@RequiredArgsConstructor
public class DeleteDraftsDomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> delete(LocalDateTime cutoffDate) {
    final var certificates = certificateRepository.findByCertificatesRequest(
        CertificatesRequest.builder()
            .createdTo(cutoffDate)
            .statuses(List.of(Status.DRAFT, Status.DELETED_DRAFT, Status.LOCKED_DRAFT))
            .build()
    );

    if (!certificates.isEmpty()) {
      certificateRepository.remove(certificates.stream().map(Certificate::id).toList());
    }

    return certificates;
  }
}

