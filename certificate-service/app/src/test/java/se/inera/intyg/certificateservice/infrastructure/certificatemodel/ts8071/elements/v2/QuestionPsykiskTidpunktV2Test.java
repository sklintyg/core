package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class QuestionPsykiskTidpunktV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("19.3");

  @Test
  void shouldIncludeId() {
    final var element = QuestionPsykiskTidpunktV2.questionPsykiskTidpunktV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextField.builder()
        .name("När hade personen senast läkarkontakt med anledning av sin diagnos?")
        .label("Ange tidpunkt")
        .id(new FieldId("19.3"))
        .build();

    final var element = QuestionPsykiskTidpunktV2.questionPsykiskTidpunktV2();

    assertEquals(expectedConfiguration, element.configuration());
  }
}

