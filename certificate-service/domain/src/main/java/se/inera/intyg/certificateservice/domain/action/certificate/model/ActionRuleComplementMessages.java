package se.inera.intyg.certificateservice.domain.action.certificate.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

public class ActionRuleComplementMessages implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return certificate.map(value -> value.messages(MessageType.COMPLEMENT)
        .stream().anyMatch(message -> message.type() == MessageType.COMPLEMENT
            && message.status() != MessageStatus.HANDLED
            && message.answer() == null)).orElse(false);
  }
}
