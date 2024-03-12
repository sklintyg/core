package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.Collections;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateDataValidationConverter;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CertificateDataValidationConverterTest {

  private static final String QUESTION_ID = "questionId";
  private static final String EXPRESSION = "expression";
  private final CertificateDataValidationConverter converter = new CertificateDataValidationConverter();

  @Test
  void shallReturnEmptyArrayForNullRules() {
    CertificateDataValidation[] result = converter.convert(null);

    assertEquals(0, result.length);
  }

  @Nested
  class ValidationMandatory {

    @Test
    void shallConvertMandatoryRuleToCertificateDataValidationMandatory() {
      final var mandatoryRule = ElementRule.builder()
          .type(ElementRuleType.MANDATORY)
          .id(new ElementId(QUESTION_ID))
          .expression(new RuleExpression(EXPRESSION))
          .build();
      final var rules = Collections.singletonList(mandatoryRule);

      final var result = converter.convert(rules);

      assertInstanceOf(CertificateDataValidationMandatory.class, result[0]);
    }

    @Test
    void shallSetCorrectQuestionIdForMandatoryValidation() {
      final var mandatoryRule = ElementRule.builder()
          .type(ElementRuleType.MANDATORY)
          .id(new ElementId(QUESTION_ID))
          .expression(new RuleExpression(EXPRESSION))
          .build();

      final var rules = Collections.singletonList(mandatoryRule);

      final var result = converter.convert(rules);

      assertEquals(QUESTION_ID, ((CertificateDataValidationMandatory) result[0]).getQuestionId());
    }

    @Test
    void shallSetCorrectExpressionForMandatoryValidation() {
      final var mandatoryRule = ElementRule.builder()
          .type(ElementRuleType.MANDATORY)
          .id(new ElementId(QUESTION_ID))
          .expression(new RuleExpression(EXPRESSION))
          .build();

      final var rules = Collections.singletonList(mandatoryRule);

      final var result = converter.convert(rules);

      assertEquals(EXPRESSION, ((CertificateDataValidationMandatory) result[0]).getExpression());
    }
  }
}
