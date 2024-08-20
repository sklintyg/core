package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class GetCertificateEventsDomainService {

  private final CertificateRepository certificateRepository;
  private final GetCertificateEventsOfTypeDomainService getCertificateEventsOfTypeDomainService;

  public List<CertificateEvent> get(CertificateId certificateId,
      ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.READ, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to read certificate %s, cannot get events".formatted(certificate.id()),
          certificate.reasonNotAllowed(CertificateActionType.READ, Optional.of(actionEvaluation))
      );
    }

    return eventsOf(certificate);
  }

  private List<CertificateEvent> eventsOf(Certificate certificate) {
    final var eventTypes = Arrays.asList(CertificateEventType.values());

    return eventTypes.stream()
        .map(type -> getCertificateEventsOfTypeDomainService.events(certificate, type))
        .flatMap(List::stream)
        .filter(event -> event.certificateId() != null)
        .sorted(Comparator.comparing(CertificateEvent::timestamp))
        .toList();
  }
}