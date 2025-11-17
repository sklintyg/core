package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteDraftsDomainService;

@Service
@RequiredArgsConstructor
public class DeleteDraftsInternalService {

  private final DeleteDraftsDomainService deleteDraftsDomainService;
  private final CertificateConverter converter;

  @Transactional
  public DeleteDraftsResponse delete(DeleteDraftsRequest request) {
    if (request.getCutoffDate() == null) {
      throw new IllegalArgumentException("Cutoff date cannot be null");
    }

    final var deletedCertificates = deleteDraftsDomainService.delete(request.getCutoffDate());

    return DeleteDraftsResponse.builder()
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

