package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import java.util.Arrays;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleAutofill;
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

  public static ElementRule autofill(ElementId id, FieldId fieldId,
      FieldId toSetFieldId) {
    return ElementRuleAutofill.builder()
        .id(id)
        .type(ElementRuleType.AUTO_FILL)
        .expression(new RuleExpression(singleExpression(fieldId.value())))
        .fillValue(ElementValueBoolean.builder()
            .booleanId(toSetFieldId)
            .value(true)
            .build()
        )
        .build();
  }

  public static ElementRule mandatory(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression(singleExpression(fieldId.value())))
        .build();
  }

  public static ElementRule show(ElementId id, FieldId fieldId) {
    return show(
        id,
        new RuleExpression(
            singleExpression(fieldId.value())
        )
    );
  }

  public static ElementRule hide(ElementId id, FieldId fieldId) {
    return hide(
        id,
        new RuleExpression(
            singleExpression(fieldId.value())
        )
    );
  }

  public static ElementRule show(ElementId id, RuleExpression ruleExpression) {
    return ElementRuleExpression.builder()
        .type(ElementRuleType.SHOW)
        .id(id)
        .expression(ruleExpression)
        .build();
  }

  public static ElementRule showEmpty(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .type(ElementRuleType.SHOW)
        .id(id)
        .expression(
            new RuleExpression(
                empty(singleExpression(fieldId.value())
                )
            )
        )
        .build();
  }

  public static ElementRule hide(ElementId id, RuleExpression ruleExpression) {
    return ElementRuleExpression.builder()
        .type(ElementRuleType.HIDE)
        .id(id)
        .expression(ruleExpression)
        .build();
  }

  public static ElementRule showIfNot(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .type(ElementRuleType.SHOW)
        .id(id)
        .expression(
            new RuleExpression(
                multipleAndExpression(
                    null,
                    not(singleExpression(fieldId.value())),
                    notEmpty(singleExpression(fieldId.value())
                    )
                )
            )
        )
        .build();
  }

  public static String notEmpty(String expression) {
    return "!empty(" + expression + ")";
  }

  public static String empty(String expression) {
    return "empty(" + expression + ")";
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
    return mandatoryOrExist(id, List.of(fieldId));
  }

  public static ElementRule mandatoryOrExist(ElementId id, List<FieldId> fieldIds) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(
            orExists(fieldIds)
        )
        .build();
  }

  public static ElementRule showOrExist(ElementId id, List<FieldId> fieldIds) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.SHOW)
        .expression(
            orExists(fieldIds)
        )
        .build();
  }


  public static ElementRule mandatoryAndExist(ElementId id, List<FieldId> fieldIds) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.MANDATORY)
        .expression(
            new RuleExpression(
                multipleAndExpressionWithExists(
                    fieldIds.stream()
                        .map(field -> singleExpression(field.value()))
                        .toArray(String[]::new)
                )
            )
        )
        .build();
  }

  private static String multipleAndExpressionWithExists(String... expression) {
    return multipleAndExpression("exists", expression);
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

  public static ElementRule disableElement(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.DISABLE)
        .expression(
            new RuleExpression(
                singleExpression(fieldId.value())
            )
        )
        .build();
  }

  public static ElementRule disableEmptyElement(ElementId id, FieldId fieldId) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.DISABLE)
        .expression(
            new RuleExpression(
                empty(singleExpression(fieldId.value()))
            )
        )
        .build();
  }


  public static ElementRule disableSubElements(ElementId id, List<FieldId> elementsForExpression,
      List<FieldId> elementsToDisable) {
    return ElementRuleExpression.builder()
        .id(id)
        .type(ElementRuleType.DISABLE_SUB_ELEMENT)
        .affectedSubElements(elementsToDisable)
        .expression(
            new RuleExpression(
                multipleOrExpressionWithExists(
                    elementsForExpression.stream()
                        .map(FieldId::value)
                        .toArray(String[]::new)
                )
            )
        )
        .build();
  }

  public static ElementRule visualAcuities(ElementId parent, FieldId leftEye,
      FieldId rightEye) {
    return CertificateElementRuleFactory.show(parent,
        new RuleExpression(
            multipleOrExpression(
                wrapWithParenthesis(
                    multipleAndExpressions(
                        wrapWithParenthesis(
                            multipleAndExpressions(
                                lessThan(
                                    withCitation(leftEye.value()),
                                    "0.8"
                                ),
                                lessThan(
                                    withCitation(rightEye.value()),
                                    "0.8"
                                )
                            )
                        ),
                        wrapWithParenthesis(
                            multipleAndExpressions(
                                wrapWithNotEmpty(
                                    withCitation(leftEye.value())),
                                wrapWithNotEmpty(
                                    withCitation(rightEye.value()))
                            )
                        )
                    )
                ),
                wrapWithParenthesis(
                    multipleAndExpressions(
                        multipleAndExpressions(
                            wrapWithParenthesis(
                                multipleOrExpression(
                                    lessThan(
                                        withCitation(leftEye.value()),
                                        "0.1"
                                    ),
                                    lessThan(
                                        withCitation(rightEye.value()),
                                        "0.1"
                                    )
                                )
                            )
                        ),
                        wrapWithParenthesis(
                            multipleAndExpressions(
                                wrapWithNotEmpty(
                                    withCitation(leftEye.value())),
                                wrapWithNotEmpty(
                                    withCitation(rightEye.value()))
                            )
                        )
                    )
                )
            )
        )
    );
  }

  public static String singleExpression(String id) {
    return "$" + id;
  }

  public static String multipleOrExpression(String... expression) {
    return multipleOrExpressionWithWrapsWith(null, expression);
  }

  private static String multipleOrExpressionWithWrapsWith(String wrapWith, String... expression) {
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

  public static String multipleAndExpression(String wrapWith, String... expression) {
    return Arrays.stream(expression).reduce("", (s, s2) -> {
      if (!s.isEmpty()) {
        s += " && ";
      }
      if (wrapWith != null) {
        s += wrapWith + "(" + s2 + ")";
      } else {
        s += s2;
      }
      return s;
    });
  }

  private static RuleExpression orExists(List<FieldId> fieldIds) {
    return new RuleExpression(
        multipleOrExpressionWithExists(
            fieldIds.stream()
                .map(field -> singleExpression(field.value()))
                .toArray(String[]::new)
        )
    );
  }

  public static String multipleOrExpressionWithExists(String... expression) {
    return multipleOrExpressionWithWrapsWith("exists", expression);
  }

  public static String multipleOrExpressionWithNotEmpty(String... expression) {
    return multipleOrExpressionWithWrapsWith("!empty", expression);
  }

  public static String not(String s) {
    return String.format("!%s", s);
  }

  public static String lessThanOrEqual(String s1, String s2) {
    return s1 + " <= " + s2;
  }

  public static String lessThan(String s1, String s2) {
    return s1 + " < " + s2;
  }

  public static String withCitation(String field) {
    return "'" + field + "'";
  }

  public static String multipleAndExpressions(String... expression) {
    return Arrays.stream(expression).reduce("", (s, s2) -> {
      if (!s.isEmpty()) {
        s += " && ";
      }
      s += s2;
      return s;
    });
  }

  public static String wrapWithParenthesis(String expression) {
    return "(" + expression + ")";
  }

  public static String exists(String expression) {
    return String.format("exists(%s)", expression);
  }

  public static String wrapWithNotEmpty(String expression) {
    return "!empty(" + expression + ")";
  }
}