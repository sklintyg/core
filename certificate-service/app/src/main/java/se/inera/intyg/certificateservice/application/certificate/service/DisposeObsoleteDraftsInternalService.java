package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.DisposeObsoleteDraftsDomainService;

@Service
@RequiredArgsConstructor
public class DisposeObsoleteDraftsInternalService {

  private final DisposeObsoleteDraftsDomainService disposeObsoleteDraftsDomainService;
  private final CertificateConverter converter;

  public ListObsoleteDraftsResponse list(ListObsoleteDraftsRequest request) {
    if (request.getCutoffDate() == null) {
      throw new IllegalArgumentException("Cutoff date cannot be null");
    }

    final var drafts = disposeObsoleteDraftsDomainService.list(request.getCutoffDate());

    return ListObsoleteDraftsResponse.builder()
        .certificateIds(
            drafts.stream()
                .map(CertificateId::id)
                .toList()
        )
        .build();
  }

  @Transactional
  public DisposeObsoleteDraftsResponse delete(DisposeObsoleteDraftsRequest request) {
    if (request.getCertificateId() == null) {
      throw new IllegalArgumentException("Certificate ID cannot be null");
    }

    final var deletedCertificate = disposeObsoleteDraftsDomainService.delete(
        new CertificateId(request.getCertificateId())
    );

    return DisposeObsoleteDraftsResponse.builder()
        .certificate(converter.convert(deletedCertificate, Collections.emptyList(), null))
        .build();
  }
}

