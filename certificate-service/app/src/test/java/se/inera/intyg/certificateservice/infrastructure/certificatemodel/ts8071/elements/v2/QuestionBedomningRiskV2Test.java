package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class QuestionBedomningRiskV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("23.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Du bedömer att det finns en risk, ange orsaken till detta")
        .id(new FieldId("23.3"))
        .build();

    final var element = QuestionBedomningRiskV2.questionBedomningRiskV2();

    assertEquals(expectedConfiguration, element.configuration());
  }
}

