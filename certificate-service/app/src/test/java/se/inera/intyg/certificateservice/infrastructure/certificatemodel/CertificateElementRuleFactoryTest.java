package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;

class CertificateElementRuleFactoryTest {

  @Test
  void shouldReturnMandatoryRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression("$FIELD"))
        .build();

    final var response = CertificateElementRuleFactory.mandatory(
        new ElementId("ID"),
        new FieldId("FIELD")
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnMandatoryRuleForSeveralFields() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression("$FIELD0 || $FIELD1 || $FIELD2"))
        .build();

    final var response = CertificateElementRuleFactory.mandatory(
        new ElementId("ID"),
        List.of(new FieldId("FIELD0"), new FieldId("FIELD1"), new FieldId("FIELD2"))
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnLimitRule() {
    final var expected = ElementRuleLimit.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.TEXT_LIMIT)
        .limit(new RuleLimit((short) 100))
        .build();

    final var response = CertificateElementRuleFactory.limit(
        new ElementId("ID"),
        (short) 100
    );

    assertEquals(expected, response);
  }

}