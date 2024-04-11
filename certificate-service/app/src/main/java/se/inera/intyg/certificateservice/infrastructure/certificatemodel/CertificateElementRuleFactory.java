package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import java.util.Arrays;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

public class CertificateElementRuleFactory {

  private CertificateElementRuleFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementRule mandatory(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression(singleExpression(fieldId.value())))
        .build();
  }

  public static ElementRule mandatory(ElementId id, List<FieldId> fieldIds) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(
            new RuleExpression(
                multipleOrExpression(
                    fieldIds.stream()
                        .map(field -> singleExpression(field.value()))
                        .toArray(String[]::new)
                )
            )
        )
        .build();
  }

  private static String singleExpression(String id) {
    return "$" + id;
  }

  private static String multipleOrExpression(String... expression) {
    return Arrays.stream(expression).reduce("", (s, s2) -> {
      if (!s.isEmpty()) {
        s += " || ";
      }
      s += s2;
      return s;
    });
  }
}
