package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
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
        CertificateElementRuleFactory.mandatory(
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
    final var elementSpecification = QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus(expectedChild);
    assertEquals(List.of(expectedChild), elementSpecification.children());
  }
}