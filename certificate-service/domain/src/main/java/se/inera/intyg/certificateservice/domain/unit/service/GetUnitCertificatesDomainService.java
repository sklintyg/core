package se.inera.intyg.certificateservice.domain.unit.service;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

@RequiredArgsConstructor
public class GetUnitCertificatesDomainService {

  private final CertificateRepository certificateRepository;

  public List<Certificate> get(CertificatesRequest request, ActionEvaluation actionEvaluation) {
    return certificateRepository.findByCertificatesRequest(
            request.apply(actionEvaluation)
        ).stream()
        .filter(certificate -> certificate.allowTo(CertificateActionType.READ,
            Optional.of(actionEvaluation)))
        .filter(filterOnValid(request))
        .toList();
  }

  private static Predicate<Certificate> filterOnValid(CertificatesRequest request) {
    return certificate -> request.validCertificates() == null
        || (FALSE.equals(request.validCertificates()) && certificate.validate().isInvalid())
        || (TRUE.equals(request.validCertificates()) && certificate.validate().isValid());
  }
}

