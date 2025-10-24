package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationVisualAcuities;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

class QuestionSynskarpaTest {

  private static final ElementId ELEMENT_ID = new ElementId("5");

  @Test
  void shallIncludeId() {
    final var element = QuestionSynskarpa.questionSynskarpa();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationVisualAcuities.builder()
        .id(new FieldId("5"))
        .name("Synskärpa")
        .withCorrectionLabel("Med korrektion")
        .withoutCorrectionLabel("Utan korrektion")
        .min(0.0)
        .max(2.0)
        .rightEye(
            ElementVisualAcuity.builder()
                .label("Höger öga")
                .withoutCorrectionId("5.1")
                .withCorrectionId("5.4")
                .build()
        )
        .leftEye(
            ElementVisualAcuity.builder()
                .label("Vänster öga")
                .withoutCorrectionId("5.2")
                .withCorrectionId("5.5")
                .build()
        )
        .binocular(
            ElementVisualAcuity.builder()
                .label("Binokulärt")
                .withoutCorrectionId("5.3")
                .withCorrectionId("5.6")
                .build()
        )
        .build();

    final var element = QuestionSynskarpa.questionSynskarpa();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        CertificateElementRuleFactory.showIfNot(
            QUESTION_SYNFUNKTIONER_ID,
            QUESTION_SYNFUNKTIONER_FIELD_ID
        ),
        CertificateElementRuleFactory.mandatoryAndExist(
            QUESTION_SYNSKARPA_ID,
            List.of(
                new FieldId("5.1"),
                new FieldId("5.2"),
                new FieldId("5.3")
            )
        )
    );

    final var element = QuestionSynskarpa.questionSynskarpa();

    assertEquals(expectedRule, element.rules());
  }

  @Nested
  class ValidationTests {

    @Test
    void shallIncludeValidation() {
      final var validation = (ElementValidationVisualAcuities) QuestionSynskarpa.questionSynskarpa()
          .validations().getFirst();

      assertAll(
          () -> assertEquals(0.0, validation.min()),
          () -> assertEquals(2.0, validation.max()),
          () -> assertEquals(0.1, validation.minAllowedSightOneEye()),
          () -> assertEquals(0.8, validation.minAllowedSightOtherEye()),
          () -> assertTrue(validation.mandatory())
      );
    }
  }
}