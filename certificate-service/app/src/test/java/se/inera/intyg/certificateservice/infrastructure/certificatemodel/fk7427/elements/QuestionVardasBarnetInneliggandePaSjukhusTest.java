package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfRadioOption;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

class QuestionVardasBarnetInneliggandePaSjukhusTest {

  private static final ElementId ELEMENT_ID = new ElementId("62.1");

  @Test
  void shallIncludeId() {
    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(ELEMENT_ID, elementSpecification.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var configurationRadioBoolean = ElementConfigurationRadioBoolean.builder()
        .id(new FieldId("62.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .name(
            "Vårdas barnet inneliggande på sjukhus?")
        .build();

    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(configurationRadioBoolean, elementSpecification.configuration());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidation = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(expectedValidation, elementSpecification.validations());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        CertificateElementRuleFactory.mandatoryExist(
            new ElementId("62.1"),
            new FieldId("62.1")
        )
    );

    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(expectedRules, elementSpecification.rules());
  }

  @Test
  void shallIncludeChildren() {
    final var expectedChild = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus(
        expectedChild);
    assertEquals(List.of(expectedChild), elementSpecification.children());
  }

  @Test
  void shallIncludeMapping() {
    final var expectedMapping = new ElementMapping(new ElementId("62"), null);
    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(expectedMapping, elementSpecification.mapping());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expectedPdfConfiguration = PdfConfigurationRadioBoolean.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].RadioButtonList_2[0]"))
        .optionTrue(new PdfRadioOption("1"))
        .optionFalse(new PdfRadioOption("2"))
        .build();

    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(expectedPdfConfiguration, elementSpecification.pdfConfiguration());
  }

  @Test
  void shouldHaveIncludeWhenRenewingFalse() {
    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus();
    assertFalse(elementSpecification.includeWhenRenewing());
  }
}
