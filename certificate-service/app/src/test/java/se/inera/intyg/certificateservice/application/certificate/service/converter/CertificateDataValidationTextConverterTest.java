package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;

class CertificateDataValidationTextConverterTest {

  private static final String ID = "questionId";
  private static final Integer LIMIT = 100;
  private final CertificateDataValidationTextConverter converter = new CertificateDataValidationTextConverter();

  @Test
  void shallConvertMandatoryRuleToCertificateDataValidationText() {
    final var rule = ElementRule.builder()
        .type(ElementRuleType.TEXT_LIMIT)
        .id(new ElementId(ID))
        .rule(new RuleLimit(LIMIT))
        .build();

    final var result = converter.convert(rule);

    assertInstanceOf(CertificateDataValidationText.class, result);
  }

  @Test
  void shallSetCorrectIdForMandatoryValidation() {
    final var rule = ElementRule.builder()
        .type(ElementRuleType.TEXT_LIMIT)
        .id(new ElementId(ID))
        .rule(new RuleLimit(LIMIT))
        .build();

    final var result = converter.convert(rule);

    assertEquals(ID, ((CertificateDataValidationText) result).getId());
  }

  @Test
  void shallSetCorrectLimitForMandatoryValidation() {
    final var rule = ElementRule.builder()
        .type(ElementRuleType.TEXT_LIMIT)
        .id(new ElementId(ID))
        .rule(new RuleLimit(LIMIT))
        .build();

    final var result = converter.convert(rule);

    assertEquals(LIMIT, ((CertificateDataValidationText) result).getLimit());
  }
}