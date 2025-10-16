package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleAutofill;
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
  void shouldReturnAutoFillRule() {
    final var fieldToFill = new FieldId("FIELDTOFILL");
    final var elementToFill = ElementValueBoolean.builder()
        .booleanId(fieldToFill)
        .value(true)
        .build();
    final var expected = ElementRuleAutofill.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.AUTO_FILL)
        .expression(new RuleExpression("$FIELD"))
        .fillValue(elementToFill)
        .build();

    final var response = CertificateElementRuleFactory.autofill(
        new ElementId("ID"),
        new FieldId("FIELD"),
        fieldToFill
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnShowEmptyRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.SHOW)
        .expression(new RuleExpression("empty($FIELD)"))
        .build();

    final var response = CertificateElementRuleFactory.showEmpty(
        new ElementId("ID"),
        new FieldId("FIELD")
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnDisableElementRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.DISABLE)
        .expression(new RuleExpression("$FIELD"))
        .build();

    final var response = CertificateElementRuleFactory.disableElement(
        new ElementId("ID"),
        new FieldId("FIELD")
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnDisableEmptyElementRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.DISABLE)
        .expression(new RuleExpression("empty($FIELD)"))
        .build();

    final var response = CertificateElementRuleFactory.disableEmptyElement(
        new ElementId("ID"),
        new FieldId("FIELD")
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnShowRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.SHOW)
        .expression(new RuleExpression("$FIELD"))
        .build();

    final var response = CertificateElementRuleFactory.show(
        new ElementId("ID"),
        new FieldId("FIELD")
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnShowIfNotRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.SHOW)
        .expression(new RuleExpression("!$FIELD && !empty($FIELD)"))
        .build();

    final var response = CertificateElementRuleFactory.showIfNot(
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
  void shouldReturnMandatoryOrExistRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression("exists($FIELD1)"))
        .build();

    final var response = CertificateElementRuleFactory.mandatoryExist(
        new ElementId("ID"), new FieldId("FIELD1")
    );

    assertEquals(expected, response);
  }


  @Test
  void shouldReturnMandatoryOrExistRuleForSeveralFields() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression("exists($FIELD0) || exists($FIELD1) || exists($FIELD2)"))
        .build();

    final var response = CertificateElementRuleFactory.mandatoryOrExist(
        new ElementId("ID"),
        List.of(new FieldId("FIELD0"), new FieldId("FIELD1"), new FieldId("FIELD2"))
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnMandatoryAndExistRuleForSeveralFields() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression("exists($FIELD0) && exists($FIELD1) && exists($FIELD2)"))
        .build();

    final var response = CertificateElementRuleFactory.mandatoryAndExist(
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

  @Test
  void shouldReturnMandatoryNotEmptyRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression("!empty($FIELD1)"))
        .build();

    final var response = CertificateElementRuleFactory.mandatoryNotEmpty(
        new ElementId("ID"), List.of(new FieldId("FIELD1"))
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnMandatoryNotEmptyRuleForSeveralFields() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.MANDATORY)
        .expression(new RuleExpression("!empty($FIELD0) || !empty($FIELD1) || !empty($FIELD2)"))
        .build();

    final var response = CertificateElementRuleFactory.mandatoryNotEmpty(
        new ElementId("ID"),
        List.of(new FieldId("FIELD0"), new FieldId("FIELD1"), new FieldId("FIELD2"))
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnLessThanOrEqual() {
    final var expectedResult = "1 <= 2";
    final var response = CertificateElementRuleFactory.lessThanOrEqual("1", "2");
    assertEquals(expectedResult, response);
  }

  @Test
  void shouldReturnWithCitations() {
    final var expectedResult = "'expected'";
    final var response = CertificateElementRuleFactory.withCitation("expected");
    assertEquals(expectedResult, response);
  }

  @Test
  void shouldReturnMultipleAndExpressions() {
    final var expectedResult = "1 && 2 && 3";
    final var response = CertificateElementRuleFactory.multipleAndExpressions("1", "2", "3");
    assertEquals(expectedResult, response);
  }

  @Test
  void shouldReturnWrapWithParenthesis() {
    final var expectedResult = "(expected)";
    final var response = CertificateElementRuleFactory.wrapWithParenthesis("expected");
    assertEquals(expectedResult, response);
  }

  @Test
  void shouldReturnLessThan() {
    final var expectedResult = "1 < 2";
    final var response = CertificateElementRuleFactory.lessThan("1", "2");
    assertEquals(expectedResult, response);
  }

  @Test
  void shouldReturnExist() {
    final var expectedResult = "exists(1)";
    final var response = CertificateElementRuleFactory.exists("1");
    assertEquals(expectedResult, response);
  }

  @Test
  void shouldReturnWrapWithNotEmpty() {
    final var expectedResult = "!empty(1)";
    final var response = CertificateElementRuleFactory.wrapWithNotEmpty("1");
    assertEquals(expectedResult, response);
  }

  @Test
  void shouldReturnHideRule() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.HIDE)
        .expression(new RuleExpression("$FIELD"))
        .build();

    final var response = CertificateElementRuleFactory.hide(
        new ElementId("ID"),
        new FieldId("FIELD")
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnHideRuleWithExpression() {
    final var expected = ElementRuleExpression.builder()
        .id(new ElementId("ID"))
        .type(ElementRuleType.HIDE)
        .expression(new RuleExpression("someExpression"))
        .build();

    final var response = CertificateElementRuleFactory.hide(
        new ElementId("ID"),
        new RuleExpression("someExpression")
    );

    assertEquals(expected, response);
  }
}
