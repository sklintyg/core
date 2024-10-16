package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import java.util.Arrays;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;

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

  public static ElementRule show(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .type(ElementRuleType.SHOW)
        .id(id)
        .expression(
            new RuleExpression(
                singleExpression(fieldId.value())
            )
        )
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

  public static ElementRule mandatoryExist(ElementId id, FieldId fieldId) {
    return mandatoryExist(id, List.of(fieldId));
  }

  public static ElementRule mandatoryExist(ElementId id, List<FieldId> fieldIds) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(
            new RuleExpression(
                multipleOrExpressionWithExists(
                    fieldIds.stream()
                        .map(field -> singleExpression(field.value()))
                        .toArray(String[]::new)
                )
            )
        )
        .build();
  }

  public static ElementRule mandatoryNotEmpty(ElementId id, List<FieldId> fieldIds) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(
            new RuleExpression(
                multipleOrExpressionWithNotEmpty(
                    fieldIds.stream()
                        .map(field -> singleExpression(field.value()))
                        .toArray(String[]::new)
                )
            )
        )
        .build();
  }

  public static ElementRule limit(ElementId id, short limit) {
    return ElementRuleLimit.builder()
        .id(id)
        .type(ElementRuleType.TEXT_LIMIT)
        .limit(new RuleLimit(limit))
        .build();
  }

  private static String singleExpression(String id) {
    return "$" + id;
  }

  private static String multipleOrExpression(String... expression) {
    return multipleOrExpression(null, expression);
  }

  private static String multipleOrExpression(String wrapWith, String... expression) {
    return Arrays.stream(expression).reduce("", (s, s2) -> {
      if (!s.isEmpty()) {
        s += " || ";
      }
      if (wrapWith != null) {
        s += wrapWith + "(" + s2 + ")";
      } else {
        s += s2;
      }
      return s;
    });
  }

  public static String multipleOrExpressionWithExists(String... expression) {
    return multipleOrExpression("exists", expression);
  }

  public static String multipleOrExpressionWithNotEmpty(String... expression) {
    return multipleOrExpression("!empty", expression);
  }
}
