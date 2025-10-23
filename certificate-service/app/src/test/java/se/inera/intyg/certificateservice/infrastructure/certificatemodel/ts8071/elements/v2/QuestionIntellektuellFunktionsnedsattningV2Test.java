package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class QuestionIntellektuellFunktionsnedsattningV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("24");

  @Test
  void shallIncludeId() {
    final var element = QuestionIntellektuellFunktionsnedsattningV2.questionIntellektuellFunktionsnedsattningV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Har personen någon intellektuell funktionsnedsättning?")
        .id(new FieldId("24.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionIntellektuellFunktionsnedsattningV2.questionIntellektuellFunktionsnedsattningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }
}

