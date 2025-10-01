package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationAutoFill;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleAutofill;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CertificateDataValidationAutoFillConverterTest {

  private static final String QUESTION_ID = "questionId";
  private static final String FIELD_ID = "fieldId";
  private static final String EXPRESSION = "expression";
  private final CertificateDataValidationAutoFillConverter converter = new CertificateDataValidationAutoFillConverter();

  @Test
  void shallConvertAutofillRuleToCertificateDataValidationAutofill() {
    final var autofillRule = ElementRuleAutofill.builder()
        .type(ElementRuleType.AUTO_FILL)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .fillValue(ElementValueBoolean.builder()
            .booleanId(new FieldId(FIELD_ID))
            .value(true)
            .build()
        )
        .build();

    final var result = converter.convert(autofillRule);

    assertInstanceOf(CertificateDataValidationAutoFill.class, result);
  }

  @Test
  void shallSetCorrectQuestionIdForAutofillValidation() {
    final var autofillRule = ElementRuleAutofill.builder()
        .type(ElementRuleType.AUTO_FILL)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .fillValue(ElementValueBoolean.builder()
            .booleanId(new FieldId(FIELD_ID))
            .value(true)
            .build()
        )
        .build();

    final var result = converter.convert(autofillRule);

    assertEquals(QUESTION_ID, ((CertificateDataValidationAutoFill) result).getQuestionId());
  }

  @Test
  void shallSetCorrectExpressionForAutofillValidation() {
    final var disableRule = ElementRuleAutofill.builder()
        .type(ElementRuleType.AUTO_FILL)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .fillValue(ElementValueBoolean.builder()
            .booleanId(new FieldId(FIELD_ID))
            .value(true)
            .build()
        )
        .build();

    final var result = converter.convert(disableRule);

    assertEquals(EXPRESSION, ((CertificateDataValidationAutoFill) result).getExpression());
  }

  @Test
  void shallThrowIfWrongType() {
    final var rule = ElementRuleLimit.builder().build();
    assertThrows(IllegalStateException.class, () -> converter.convert(rule));
  }

  @Test
  void shallThrowIfFillValueIsNull() {
    final var rule = ElementRuleAutofill.builder()
        .type(ElementRuleType.AUTO_FILL)
        .id(new ElementId(QUESTION_ID))
        .expression(new RuleExpression(EXPRESSION))
        .fillValue(null)
        .build();
    assertThrows(IllegalStateException.class, () -> converter.convert(rule));
  }

}