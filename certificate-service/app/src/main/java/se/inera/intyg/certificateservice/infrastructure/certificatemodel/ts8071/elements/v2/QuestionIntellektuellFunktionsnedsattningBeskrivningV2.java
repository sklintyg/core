package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionIntellektuellFunktionsnedsattningV2.QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionIntellektuellFunktionsnedsattningV2.QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionIntellektuellFunktionsnedsattningBeskrivningV2 {

  public static final ElementId QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_BESKRIVNING_V2_ID =
      new ElementId("26.2");
  public static final FieldId QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_BESKRIVNING_V2_FIELD_ID =
      new FieldId("26.2");

  private QuestionIntellektuellFunktionsnedsattningBeskrivningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionIntellektuellFunktionsnedsattningBeskrivningV2() {
    return ElementSpecification.builder()
        .id(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_BESKRIVNING_V2_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_BESKRIVNING_V2_FIELD_ID)
                .name("Vilken diagnos och grad?")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID,
                    QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_BESKRIVNING_V2_ID,
                    QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_BESKRIVNING_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_BESKRIVNING_V2_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(
                QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID, null)
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(250)
                    .build()
            )
        )
        .build();
  }
}

