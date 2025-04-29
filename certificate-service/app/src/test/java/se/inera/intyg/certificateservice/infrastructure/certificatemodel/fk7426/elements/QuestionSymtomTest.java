package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.FK7426PdfSpecification.ROW_MAX_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionSymtom.questionSymtom;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionSymtomTest {

  private static final ElementId ELEMENT_ID = new ElementId("55");
  private static final FieldId FIELD_ID = new FieldId("55.1");

  @Test
  void shallIncludeId() {
    final var element = questionSymtom();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Fyll i vilka symtom barnet har om diagnos inte är fastställd")
        .id(FIELD_ID)
        .build();

    final var element = questionSymtom();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = questionSymtom();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .limit(4000)
            .build()
    );

    final var element = questionSymtom();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtFlerradig[0]"))
        .maxLength(ROW_MAX_LENGTH * 5)
        .overflowSheetFieldId(
            new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
        .build();

    final var element = questionSymtom();

    assertEquals(expected, element.pdfConfiguration());
  }
}
