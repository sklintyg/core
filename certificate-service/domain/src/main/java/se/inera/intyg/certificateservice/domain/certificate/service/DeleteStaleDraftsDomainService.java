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

  public List<Certificate> delete(CertificateId certificateId) {
    final var certificates = certificateRepository.findByIds(List.of(certificateId));

    if (certificates.isEmpty()) {
      return List.of();
    }

    final var certificate = certificates.getFirst();

    if (!ALLOWED_STATUSES.contains(certificate.status())) {
      throw new IllegalStateException(
          String.format("Cannot delete certificate with id %s wrong status %s",
              certificate.id().id(), certificate.status()));
    }

    certificateRepository.remove(List.of(certificateId));

    return List.of(certificate);
  }
}

