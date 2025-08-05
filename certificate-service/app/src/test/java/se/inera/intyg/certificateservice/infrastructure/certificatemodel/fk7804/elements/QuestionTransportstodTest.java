package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionTransportstodTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionTransportstod.questionTransportstod();
    assertEquals(new ElementId("34"), element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expected = ElementConfigurationCheckboxBoolean.builder()
        .id(new FieldId("34.1"))
        .label(
            "Patienten skulle kunna arbeta helt eller delvis vid hjälp med transport till och från arbetsplatsen")
        .selectedText("Ja")
        .unselectedText("Ej angivet")
        .build();
    final var element = QuestionTransportstod.questionTransportstod();
    assertEquals(expected, element.configuration());
  }

  @Test
  void shouldIncludeValidation() {
    final var expected = List.of(
        ElementValidationBoolean.builder()
            .mandatory(false)
            .build()
    );
    final var element = QuestionTransportstod.questionTransportstod();
    assertEquals(expected, element.validations());
  }
}

