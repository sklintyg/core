package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;

@Value
@Builder
public class ElementRuleAutofill implements ElementRule {

  ElementId id;
  ElementRuleType type;
  RuleExpression expression;
  ElementValueBoolean fillValue;

}
