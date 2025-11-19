package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteStaleDraftsDomainService;

@Service
@RequiredArgsConstructor
public class DeleteStaleDraftsInternalService {

  private final DeleteStaleDraftsDomainService deleteStaleDraftsDomainService;
  private final CertificateConverter converter;

  @Transactional
  public ListStaleDraftsResponse list(ListStaleDraftsRequest request) {
    if (request.getCutoffDate() == null) {
      throw new IllegalArgumentException("Cutoff date cannot be null");
    }

    final var staleDrafts = deleteStaleDraftsDomainService.list(request.getCutoffDate());

    return ListStaleDraftsResponse.builder()
        .certificateIds(
            staleDrafts.stream()
                .map(CertificateId::id)
                .toList()
        )
        .build();
  }

  @Transactional
  public DeleteStaleDraftsResponse delete(DeleteStaleDraftsRequest request) {
    if (request.getCertificateId() == null) {
      throw new IllegalArgumentException("Certificate ID cannot be null");
    }

    final var deletedCertificate = deleteStaleDraftsDomainService.delete(
        new CertificateId(request.getCertificateId())
    );

    return DeleteStaleDraftsResponse.builder()
        .certificate(converter.convert(deletedCertificate, Collections.emptyList(), null))
        .build();
  }
}

