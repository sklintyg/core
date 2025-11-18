package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@RequiredArgsConstructor
public class DeleteStaleDraftsDomainService {

  private static final List<Status> ALLOWED_STATUSES = List.of(Status.DRAFT, Status.DELETED_DRAFT,
      Status.LOCKED_DRAFT);

  private final CertificateRepository certificateRepository;

  public List<Certificate> list(LocalDateTime cutoffDate) {
    return certificateRepository.findByCertificatesRequest(
        CertificatesRequest.builder()
            .createdTo(cutoffDate)
            .statuses(ALLOWED_STATUSES)
            .build()
    );
  }

  public List<Certificate> delete(List<CertificateId> certificateIds) {
    final var certificates = certificateRepository.findByIds(certificateIds);

    final var invalidCertificate = certificates.stream()
        .filter(certificate -> !ALLOWED_STATUSES.contains(certificate.status()))
        .findFirst();

    if (invalidCertificate.isPresent()) {
      throw new IllegalStateException(
          "Cannot delete certificate with id: " + invalidCertificate.get().id().id()
              + " and status: " + invalidCertificate.get().status()
      );
    }

    if (!certificates.isEmpty()) {
      certificateRepository.remove(certificateIds);
    }

    return certificates;
  }
}

