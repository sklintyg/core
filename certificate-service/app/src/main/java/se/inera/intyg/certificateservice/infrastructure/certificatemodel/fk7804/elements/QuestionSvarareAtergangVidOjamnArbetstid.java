package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0003;

public class QuestionSvarareAtergangVidOjamnArbetstid {

  public static final ElementId QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID = new ElementId(
      "33");
  public static final FieldId QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_FIELD_ID = new FieldId(
      "33.1");

  private QuestionSvarareAtergangVidOjamnArbetstid() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSvarareAtergangVidOjamnArbetstid() {
    return ElementSpecification.builder()
        .id(QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_FIELD_ID)
                .name(
                    "Kommer möjligheterna till återgång i arbete försämras om arbetstiden förläggs ojämnt vid deltidssjukskrivning?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID,
                    QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_FIELD_ID
                ),
                CertificateElementRuleFactory.showOrExist(
                    QUESTION_NEDSATTNING_ARBETSFORMAGA_ID,
                    List.of(
                        new FieldId(CodeSystemKvFkmu0003.HALFTEN.code()),
                        new FieldId(CodeSystemKvFkmu0003.TRE_FJARDEDEL.code()),
                        new FieldId(CodeSystemKvFkmu0003.EN_FJARDEDEL.code()
                        )
                    )
                )
            )
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}

