package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionMedicinskBehandling {

  public static final ElementId QUESTION_MEDICINSK_BEHANDLING_ID = new ElementId("19");
  public static final FieldId QUESTION_MEDICINSK_BEHANDLING_FIELD_ID = new FieldId("19.1");

  private QuestionMedicinskBehandling() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicinskBehandling(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MEDICINSK_BEHANDLING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Beskriv pågående och planerade medicinska behandlingar/åtgärder som kan påverka arbetsförmågan")
                .label("Ange vad syftet är och om möjligt tidsplan samt ansvarig vårdenhet.")
                .id(QUESTION_MEDICINSK_BEHANDLING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_MEDICINSK_BEHANDLING_ID,
                    (short) 4000),
                CertificateElementRuleFactory.hide(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false)
        )
        .children(List.of(children))
        .build();
  }
}
