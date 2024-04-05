package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;

class CertificateDataValidationTextConverterTest {

  private static final String ID = "questionId";
  private static final short LIMIT = 100;
  private final CertificateDataValidationTextConverter converter = new CertificateDataValidationTextConverter();

  @Test
  void shallConvertMandatoryRuleToCertificateDataValidationText() {
    final var rule = ElementRuleLimit.builder()
        .type(ElementRuleType.TEXT_LIMIT)
        .id(new ElementId(ID))
        .limit(new RuleLimit(LIMIT))
        .build();

    final var result = converter.convert(rule);

    assertInstanceOf(CertificateDataValidationText.class, result);
  }

  @Test
  void shallSetCorrectIdForMandatoryValidation() {
    final var rule = ElementRuleLimit.builder()
        .type(ElementRuleType.TEXT_LIMIT)
        .id(new ElementId(ID))
        .limit(new RuleLimit(LIMIT))
        .build();

    final var result = converter.convert(rule);

    assertEquals(ID, ((CertificateDataValidationText) result).getId());
  }

  @Test
  void shallSetCorrectLimitForMandatoryValidation() {
    final var rule = ElementRuleLimit.builder()
        .type(ElementRuleType.TEXT_LIMIT)
        .id(new ElementId(ID))
        .limit(new RuleLimit(LIMIT))
        .build();

    final var result = converter.convert(rule);

    assertEquals(LIMIT, ((CertificateDataValidationText) result).getLimit());
  }

  @Test
  void shallThrowIfWrongType() {
    final var rule = ElementRuleExpression.builder().build();
    assertThrows(IllegalStateException.class, () -> converter.convert(rule));
  }
}
