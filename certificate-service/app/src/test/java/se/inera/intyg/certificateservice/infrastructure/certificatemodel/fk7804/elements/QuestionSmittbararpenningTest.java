package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.questionSmittbararpenning;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionSmittbararpenningTest {

  @Test
  void shouldIncludeId() {
    final var element = questionSmittbararpenning();
    assertEquals(new ElementId("27"), element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expected = ElementConfigurationCheckboxBoolean.builder()
        .id(new FieldId("27.1"))
        .label("Förhållningsregler enligt smittskyddslagen på grund av smitta")
        .selectedText("Ja")
        .unselectedText("Ej angivet")
        .build();
    final var element = questionSmittbararpenning();
    assertEquals(expected, element.configuration());
  }

  @Test
  void shouldIncludeValidation() {
    final var expected = List.of(
        ElementValidationBoolean.builder()
            .mandatory(false)
            .build()
    );
    final var element = questionSmittbararpenning();
    assertEquals(expected, element.validations());
  }

  @Test
  void shouldHaveCorrectPdfConfiguration() {
    final var expected = PdfConfigurationBoolean.builder()
        .checkboxTrue(new PdfFieldId("form1[0].#subform[0].ksr_AvstangningSmittskyddslagen[0]"))
        .build();
    assertEquals(expected, questionSmittbararpenning().pdfConfiguration());
  }
}
