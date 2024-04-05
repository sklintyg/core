package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementRuleExpression implements ElementRule {

  ElementId id;
  ElementRuleType type;
  RuleExpression rule;
}
