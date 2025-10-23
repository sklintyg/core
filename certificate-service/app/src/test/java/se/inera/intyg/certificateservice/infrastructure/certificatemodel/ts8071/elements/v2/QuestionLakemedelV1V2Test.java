package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class QuestionLakemedelV1V2Test {

  private static final ElementId ELEMENT_ID = new ElementId("18.8");

  @Test
  void shouldIncludeId() {
    final var element = QuestionLakemedelV2.questionLakemedelV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name("Pågår läkarordinerat bruk av läkemedel som kan innebära en trafiksäkerhetsrisk?")
        .id(new FieldId("18.8"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionLakemedelV2.questionLakemedelV2();

    assertEquals(expectedConfiguration, element.configuration());
  }
}

