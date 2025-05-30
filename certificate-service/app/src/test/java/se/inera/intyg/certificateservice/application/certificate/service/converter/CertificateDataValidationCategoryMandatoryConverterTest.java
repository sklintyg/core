package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationCategoryMandatory;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.ExpressionTypeEnum;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleMandatoryCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ExpressionOperandType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CertificateDataValidationCategoryMandatoryConverterTest {

  private static final String EXPECTED_ID = "expectedId";
  private static final String EXPECTED_EXPRESSION = "expectedExpression";
  private CertificateDataValidationCategoryMandatoryConverter converter;

  @BeforeEach
  void setUp() {
    converter = new CertificateDataValidationCategoryMandatoryConverter();
  }

  @Test
  void shallReturnType() {
    assertEquals(ElementRuleType.CATEGORY_MANDATORY, converter.getType());
  }

  @Test
  void shallThrowIfWrongTypeOfRule() {
    final var elementRuleExpression = ElementRuleExpression.builder()
        .type(ElementRuleType.SHOW)
        .build();

    assertThrows(IllegalArgumentException.class, () -> converter.convert(elementRuleExpression));
  }

  @Test
  void shallReturnCertificateDataValidationCategoryMandatoryWithExpressionType() {
    final var elementRuleMandatoryCategory = ElementRuleMandatoryCategory.builder()
        .operandType(ExpressionOperandType.OR)
        .build();
    final var result = (CertificateDataValidationCategoryMandatory) converter.convert(
        elementRuleMandatoryCategory);
    assertEquals(ExpressionTypeEnum.OR, result.getExpressionType());
  }

  @Test
  void shallReturnCertificateDataValidationCategoryMandatoryWithExpression() {
    final var elementRuleMandatoryCategory = ElementRuleMandatoryCategory.builder()
        .operandType(ExpressionOperandType.OR)
        .elementRuleExpressions(
            List.of(
                ElementRuleExpression.builder()
                    .id(new ElementId(EXPECTED_ID))
                    .expression(new RuleExpression(EXPECTED_EXPRESSION))
                    .build()
            )

        )
        .build();
    final var result = (CertificateDataValidationCategoryMandatory) converter.convert(
        elementRuleMandatoryCategory);

    assertAll(
        () -> assertEquals(EXPECTED_ID, result.getQuestions().getFirst().getQuestionId()),
        () -> assertEquals(EXPECTED_EXPRESSION, result.getQuestions().getFirst().getExpression())
    );
  }
}