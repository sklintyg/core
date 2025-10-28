package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

@RequiredArgsConstructor
public class ActionRuleInactiveCertificateType implements ActionRule {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  private final CertificateModelRepository certificateModelRepository;

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    //Se över namngivning på ActionRulen så att den bättre matchar "begränsad funktionalitet"
    //Hantera konfiguration kopplat till olika actions

    final var evaluatedCertificate = certificate.orElseThrow(() -> new IllegalStateException(
        "Certificate is required for evaluating ActionRuleInactiveCertificateType"));

    final var latestMajorModelId = certificateModelRepository.findLatestActiveByType(
        evaluatedCertificate.certificateModel().typeName()
    ).orElseThrow().id();

    if (latestMajorModelId.isSameVersion(evaluatedCertificate.certificateModel().id())) {
      return true;
    }

    return false;
  }
}