package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CertificateDataValidationMandatoryConverterTest {

  private static final String QUESTION_ID = "questionId";
  private static final String EXPRESSION = "expression";
  private final CertificateDataValidationMandatoryConverter converter = new CertificateDataValidationMandatoryConverter();

  @Test
  void shallConvertMandatoryRuleToCertificateDataValidationMandatory() {
    final var mandatoryRule = ElementRuleExpression.builder()
        .type(ElementRuleType.MANDATORY)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(mandatoryRule);

    assertInstanceOf(CertificateDataValidationMandatory.class, result);
  }

  @Test
  void shallSetCorrectQuestionIdForMandatoryValidation() {
    final var mandatoryRule = ElementRuleExpression.builder()
        .type(ElementRuleType.MANDATORY)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(mandatoryRule);

    assertEquals(QUESTION_ID, ((CertificateDataValidationMandatory) result).getQuestionId());
  }

  @Test
  void shallSetCorrectExpressionForMandatoryValidation() {
    final var mandatoryRule = ElementRuleExpression.builder()
        .type(ElementRuleType.MANDATORY)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .build();

    final var result = converter.convert(mandatoryRule);

    assertEquals(EXPRESSION, ((CertificateDataValidationMandatory) result).getExpression());
  }

  @Test
  void shallThrowIfWrongType() {
    final var rule = ElementRuleLimit.builder().build();
    assertThrows(IllegalStateException.class, () -> converter.convert(rule));
  }
}
