package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementRuleMandatoryCategory implements ElementRule {

  ElementRuleType type;
  ExpressionOperandType operandType;
  @Builder.Default
  List<ElementRule> elementRuleExpressions = Collections.emptyList();
}