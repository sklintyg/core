package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementRuleLimit implements ElementRule {

  ElementId id;
  ElementRuleType type;
  RuleLimit limit;

}