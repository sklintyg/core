package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationHide;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CertificateDataValidationHideConverterTest {

  private static final String QUESTION_ID = "questionId";
  private static final String EXPRESSION = "expression";
  private final CertificateDataValidationHideConverter converter = new CertificateDataValidationHideConverter();

  @Test
  void shallConvertHideRuleToCertificateDataValidationHide() {
    final var hideRule = ElementRuleExpression.builder()
        .type(ElementRuleType.HIDE)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(hideRule);

    assertInstanceOf(CertificateDataValidationHide.class, result);
  }

  @Test
  void shallSetCorrectQuestionIdForHideValidation() {
    final var hideRule = ElementRuleExpression.builder()
        .type(ElementRuleType.HIDE)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(hideRule);

    assertEquals(QUESTION_ID, ((CertificateDataValidationHide) result).getQuestionId());
  }

  @Test
  void shallSetCorrectExpressionForHideValidation() {
    final var hideRule = ElementRuleExpression.builder()
        .type(ElementRuleType.HIDE)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(hideRule);

    assertEquals(EXPRESSION, ((CertificateDataValidationHide) result).getExpression());
  }

  @Test
  void shallThrowIfWrongType() {
    final var rule = ElementRuleLimit.builder().build();
    assertThrows(IllegalStateException.class, () -> converter.convert(rule));
  }
}

