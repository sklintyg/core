package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateXml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class GetCertificateXmlDomainService {

  private final CertificateRepository certificateRepository;
  private final XmlGenerator xmlGenerator;

  public CertificateXml get(CertificateId certificateId, ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);

    if (!certificate.allowTo(CertificateActionType.READ, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to read certificate for %s so cannot get XML".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.READ, actionEvaluation)
      );
    }

    certificate.updateMetadata(actionEvaluation);

    final var xml = xmlGenerator.generate(certificate);

    return CertificateXml.builder()
        .certificateId(certificate.id())
        .revision(certificate.revision())
        .xml(xml)
        .build();
  }
}
