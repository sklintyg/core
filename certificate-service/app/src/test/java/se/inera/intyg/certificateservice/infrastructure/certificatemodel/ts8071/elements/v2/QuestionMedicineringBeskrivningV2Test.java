package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class QuestionMedicineringBeskrivningV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("21.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionMedicineringBeskrivningV2.questionMedicineringBeskrivningV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange vilken eller vilka mediciner")
        .id(new FieldId("21.2"))
        .build();

    final var element = QuestionMedicineringBeskrivningV2.questionMedicineringBeskrivningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }
}

