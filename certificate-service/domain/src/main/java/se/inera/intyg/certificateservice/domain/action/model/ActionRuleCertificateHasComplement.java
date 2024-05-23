package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRuleCertificateHasComplement implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate.filter(
        value -> value.messages() != null && !value.messages().isEmpty()
            && value.messages().stream()
            .anyMatch(message -> message.complements() != null && !message.complements().isEmpty())
    ).isPresent();
  }
}
