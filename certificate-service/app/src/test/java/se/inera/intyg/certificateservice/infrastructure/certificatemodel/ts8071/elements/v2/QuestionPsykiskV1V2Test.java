package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class QuestionPsykiskV1V2Test {

  private static final ElementId ELEMENT_ID = new ElementId("19");

  @Test
  void shallIncludeId() {
    final var element = QuestionPsykiskV2.questionPsykiskV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen eller har personen haft psykisk sjukdom eller störning?")
        .id(new FieldId("19.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionPsykiskV2.questionPsykiskV2();

    assertEquals(expectedConfiguration, element.configuration());
  }
}

