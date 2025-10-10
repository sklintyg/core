package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorsel.QUESTION_HORSEL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorselhjalpmedel.QUESTION_HORSELHJALPMEDEL_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorselhjalpmedel.QUESTION_HORSELHJALPMEDEL_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat;

public class QuestionHorselhjalpmedelPosition {

  public static final ElementId QUESTION_HORSELHJALPMEDEL_POSITION_ID = new ElementId(
      "9.3");
  public static final FieldId QUESTION_HORSELHJALPMEDEL_POSITION_FIELD_ID = new FieldId(
      "9.3");

  private QuestionHorselhjalpmedelPosition() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselhjalpmedelPosition(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKvAnatomiskLokalisationHorapparat.VANSTER),
        CodeFactory.elementConfigurationCode(CodeSystemKvAnatomiskLokalisationHorapparat.HOGER),
        CodeFactory.elementConfigurationCode(
            CodeSystemKvAnatomiskLokalisationHorapparat.BADA_ORONEN)
    );

    return ElementSpecification.builder()
        .id(QUESTION_HORSELHJALPMEDEL_POSITION_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_HORSELHJALPMEDEL_POSITION_FIELD_ID)
                .name(
                    "Om personen behöver använda hörapparat, ange på vilket öra eller om hörapparat används på båda öronen")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_HORSELHJALPMEDEL_POSITION_ID,
                    radioMultipleCodes.stream().map(ElementConfigurationCode::id).toList()
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_HORSELHJALPMEDEL_ID,
                    QUESTION_HORSELHJALPMEDEL_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCode.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_HORSELHJALPMEDEL_ID))
        .mapping(new ElementMapping(QUESTION_HORSEL_ID, null))
        .children(List.of(children))
        .build();
  }
}