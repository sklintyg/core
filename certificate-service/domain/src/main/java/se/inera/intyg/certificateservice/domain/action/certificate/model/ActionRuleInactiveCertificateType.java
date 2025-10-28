package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@RequiredArgsConstructor
public class ActionRuleInactiveCertificateType implements ActionRule {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    //Implementera så att vi bara bryr oss om konfigurationen, om ett senare majorintyg finns.
    //Se över namngivning på ActionRulen så att den bättre matchar "begränsad funktionalitet"
    //Hantera konfiguration kopplat till olika actions

    final var inactiveConfiguration = certificateActionConfigurationRepository.findInactiveConfiguration(
        certificate.orElseThrow().certificateModel().id()
    );

    if (inactiveConfiguration.isEmpty()) {
      return true;
    }

    return true;
  }
}