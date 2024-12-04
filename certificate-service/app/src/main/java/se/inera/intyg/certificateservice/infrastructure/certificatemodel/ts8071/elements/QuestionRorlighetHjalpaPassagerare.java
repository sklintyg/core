package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionRorlighetHjalpaPassagerare {

  public static final ElementId QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID = new ElementId("10.3");
  public static final FieldId QUESTION_RORLIGHET_HJALPA_PASSAGERARE_FIELD_ID = new FieldId("10.3");

  private QuestionRorlighetHjalpaPassagerare() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionRorlighetHjalpaPassagerare(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_RORLIGHET_HJALPA_PASSAGERARE_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen en nedsättning av rörelseförmågan som gör att personen inte kan "
                        + "hjälpa passagerare in och ut ur fordonet samt med bilbälte?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules( // TODO: Add rules for show if specific group in q1
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID,
                    QUESTION_RORLIGHET_HJALPA_PASSAGERARE_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
