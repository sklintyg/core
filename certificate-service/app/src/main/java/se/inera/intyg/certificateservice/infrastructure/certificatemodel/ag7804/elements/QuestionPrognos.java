package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;

public class QuestionPrognos {

  public static final ElementId QUESTION_PROGNOS_ID = new ElementId(
      "39");
  public static final FieldId QUESTION_PROGNOS_FIELD_ID = new FieldId("39.1");

  public static final FieldId STOR_SANNOLIKHET_FIELD_ID = new FieldId("STOR_SANNOLIKHET");
  public static final FieldId ATER_X_ANTAL_MANADER_FIELD_ID = new FieldId("ATER_X_ANTAL_MANADER");
  public static final FieldId SANNOLIKT_INTE_FIELD_ID = new FieldId("SANNOLIKT_INTE");
  public static final FieldId PROGNOS_OKLAR_FIELD_ID = new FieldId("PROGNOS_OKLAR");

  private QuestionPrognos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPrognos(ElementSpecification... children) {

    final var radioMultipleCodes = List.of(
        new ElementConfigurationCode(
            STOR_SANNOLIKHET_FIELD_ID,
            CodeSystemKvFkmu0006.STOR_SANNOLIKHET.displayName(),
            CodeSystemKvFkmu0006.STOR_SANNOLIKHET
        ),
        new ElementConfigurationCode(
            ATER_X_ANTAL_MANADER_FIELD_ID,
            CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.displayName(),
            CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER
        ),
        new ElementConfigurationCode(
            SANNOLIKT_INTE_FIELD_ID,
            CodeSystemKvFkmu0006.SANNOLIKT_INTE.displayName(),
            CodeSystemKvFkmu0006.SANNOLIKT_INTE
        ),
        new ElementConfigurationCode(
            PROGNOS_OKLAR_FIELD_ID,
            CodeSystemKvFkmu0006.PROGNOS_OKLAR.displayName(),
            CodeSystemKvFkmu0006.PROGNOS_OKLAR
        )
    );

    return ElementSpecification.builder()
        .id(QUESTION_PROGNOS_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_PROGNOS_FIELD_ID)
                .name("Prognos för arbetsförmåga utifrån aktuellt undersökningstillfälle")
                .description("En viktig information för att underlätta planeringen.")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_PROGNOS_ID,
                    radioMultipleCodes.stream().map(ElementConfigurationCode::id).toList()
                ),
                CertificateElementRuleFactory.hide(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
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
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false))
        .children(List.of(children))
        .build();
  }

}
