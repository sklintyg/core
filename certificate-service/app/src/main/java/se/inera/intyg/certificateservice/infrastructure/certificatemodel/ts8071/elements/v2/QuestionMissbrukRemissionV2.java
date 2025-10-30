package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.QUESTION_MISSBRUK_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.QUESTION_MISSBRUK_V2_ID;

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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001;

public class QuestionMissbrukRemissionV2 {

  public static final ElementId QUESTION_MISSBRUK_REMISSION_V2_ID = new ElementId("18.10");
  public static final FieldId QUESTION_MISSBRUK_REMISSION_V2_FIELD_ID = new FieldId("18.10");

  private QuestionMissbrukRemissionV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukRemissionV2(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKvTs001.JA),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs001.NEJ),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs001.VET_INTE)
    );

    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_REMISSION_V2_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_MISSBRUK_REMISSION_V2_FIELD_ID)
                .name("Om diagnos beroende, är beroendet i fullständig långvarig remission?")
                .description(
                    "Här avses exempelvis beroende eller skadligt mönster av bruk enligt ICD-11, skadligt bruk enligt ICD-10, missbruk enligt DSM-IV eller substansbrukssyndrom enligt DSM-5.")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_MISSBRUK_REMISSION_V2_ID,
                    radioMultipleCodes.stream().map(ElementConfigurationCode::id).toList()
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_MISSBRUK_V2_ID,
                    QUESTION_MISSBRUK_V2_FIELD_ID
                )
            )
        )
        .shouldValidate(ElementDataPredicateFactory.valueBoolean(QUESTION_MISSBRUK_V2_ID))
        .mapping(new ElementMapping(QUESTION_MISSBRUK_V2_ID, null))
        .validations(
            List.of(
                ElementValidationCode.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}
