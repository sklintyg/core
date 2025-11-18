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
        .certificates(
            staleDrafts.stream()
                .map(draft ->
                    converter.convert(draft, Collections.emptyList(), null)
                )
                .toList()
        )
        .build();
  }

  @Transactional
  public DeleteStaleDraftsResponse delete(DeleteStaleDraftsRequest request) {
    if (request.getCertificateIds() == null || request.getCertificateIds().isEmpty()) {
      throw new IllegalArgumentException("Certificate IDs cannot be null or empty");
    }

    final var deletedCertificates = deleteStaleDraftsDomainService.delete(
        request.getCertificateIds()
            .stream()
            .map(CertificateId::new)
            .toList()
    );

    return DeleteStaleDraftsResponse.builder()
        .certificates(
            deletedCertificates.stream()
                .map(draft ->
                    converter.convert(draft, Collections.emptyList(), null)
                )
                .toList()
        )
        .build();
  }
}

