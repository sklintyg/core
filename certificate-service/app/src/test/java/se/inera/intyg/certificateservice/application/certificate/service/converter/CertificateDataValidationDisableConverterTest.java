package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationDisable;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CertificateDataValidationDisableConverterTest {

  private static final String QUESTION_ID = "questionId";
  private static final String EXPRESSION = "expression";
  private final CertificateDataValidationDisableConverter converter = new CertificateDataValidationDisableConverter();

  @Test
  void shallConvertDisableRuleToCertificateDataValidationDisable() {
    final var disableRule = ElementRuleExpression.builder()
        .type(ElementRuleType.DISABLE)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(disableRule);

    assertInstanceOf(CertificateDataValidationDisable.class, result);
  }

  @Test
  void shallSetCorrectQuestionIdForDisableValidation() {
    final var disableRule = ElementRuleExpression.builder()
        .type(ElementRuleType.DISABLE)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(disableRule);

    assertEquals(QUESTION_ID, ((CertificateDataValidationDisable) result).getQuestionId());
  }

  @Test
  void shallSetCorrectExpressionForDisableValidation() {
    final var disableRule = ElementRuleExpression.builder()
        .type(ElementRuleType.DISABLE)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(disableRule);

    assertEquals(EXPRESSION, ((CertificateDataValidationDisable) result).getExpression());
  }

  @Test
  void shallThrowIfWrongType() {
    final var rule = ElementRuleLimit.builder().build();
    assertThrows(IllegalStateException.class, () -> converter.convert(rule));
  }
}