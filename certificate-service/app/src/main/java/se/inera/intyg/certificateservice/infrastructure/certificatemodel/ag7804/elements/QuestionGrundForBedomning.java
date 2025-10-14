package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionPrognos.QUESTION_PROGNOS_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;

public class QuestionGrundForBedomning {

  public static final ElementId QUESTION_GRUND_FOR_BEDOMNING_ID = new ElementId(
      "39.2");
  private static final FieldId QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID = new FieldId("39.2");

  private QuestionGrundForBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGrundForBedomning() {
    return ElementSpecification.builder()
        .id(QUESTION_GRUND_FOR_BEDOMNING_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID)
                .name(
                    "Beskriv vad som ligger till grund för bedömningen")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_GRUND_FOR_BEDOMNING_ID,
                    QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_GRUND_FOR_BEDOMNING_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_PROGNOS_ID, new FieldId(CodeSystemKvFkmu0006.PROGNOS_OKLAR.code())
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(ElementDataPredicateFactory.codes(QUESTION_PROGNOS_ID,
            List.of(new FieldId(CodeSystemKvFkmu0006.PROGNOS_OKLAR.code()))))
        .mapping(new ElementMapping(QUESTION_PROGNOS_ID, CodeSystemKvFkmu0006.PROGNOS_OKLAR))
        .build();
  }

}
