package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

class QuestionVardasBarnetInneliggandePaSjukhusTest {

  private static final ElementId ELEMENT_ID = new ElementId("62");
  private static final FieldId FIELD_ID = new FieldId("62.1");

  @Test
  void shallIncludeId() {
    final var elementSpecification = questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(ELEMENT_ID, elementSpecification.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var configurationRadioBoolean = ElementConfigurationRadioBoolean.builder()
        .id(FIELD_ID)
        .selectedText("Ja")
        .unselectedText("Nej")
        .name("Vårdas barnet inneliggande på sjukhus?")
        .build();

    final var elementSpecification = questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(configurationRadioBoolean, elementSpecification.configuration());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidation = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var elementSpecification = questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(expectedValidation, elementSpecification.validations());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        CertificateElementRuleFactory.mandatoryExist(
            ELEMENT_ID,
            FIELD_ID
        )
    );

    final var elementSpecification = questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(expectedRules, elementSpecification.rules());
  }

  @Test
  void shallIncludeChildren() {
    final var expectedChild = questionVardasBarnetInneliggandePaSjukhus();
    final var elementSpecification = questionVardasBarnetInneliggandePaSjukhus(
        expectedChild);
    assertEquals(List.of(expectedChild), elementSpecification.children());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expectedPdfConfiguration = PdfConfigurationBoolean.builder()
        .checkboxFalse(new PdfFieldId("form1[0].#subform[3].ksr_Nej_1[0]"))
        .checkboxTrue(new PdfFieldId("form1[0].#subform[3].ksr_Ja_1[0]"))
        .build();

    final var elementSpecification = questionVardasBarnetInneliggandePaSjukhus();
    assertEquals(expectedPdfConfiguration, elementSpecification.pdfConfiguration());
  }

  @Test
  void shouldHaveIncludeWhenRenewingFalse() {
    final var elementSpecification = questionVardasBarnetInneliggandePaSjukhus();
    assertFalse(elementSpecification.includeWhenRenewing());
  }
}
