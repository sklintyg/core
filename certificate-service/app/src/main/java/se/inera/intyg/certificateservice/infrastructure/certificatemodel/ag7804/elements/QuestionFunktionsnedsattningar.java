package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.IcfCodesPropertyType;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationIcfValue;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionFunktionsnedsattningar {

  public static final ElementId QUESTION_FUNKTIONSNEDSATTNINGAR_ID = new ElementId(
      "35");
  public static final FieldId QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID = new FieldId("35.1");

  private QuestionFunktionsnedsattningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionFunktionsnedsattningar() {
    return ElementSpecification.builder()
        .id(QUESTION_FUNKTIONSNEDSATTNINGAR_ID)
        .configuration(
            ElementConfigurationIcf.builder()
                .id(QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID)
                .icfCodesPropertyName(
                    IcfCodesPropertyType.FUNKTIONSNEDSATTNINGAR)
                .name(
                    "Ange vilken/vilka funktionsnedsättningar patienten har till följd av sjukdom och om möjligt svårighetsgrad. Ange även vad din bedömning av funktionsnedsättningar baseras på. Beskriv relevanta undersökningsfynd, testresultat, utredningssvar eller andra uppgifter (exempelvis anamnesuppgifter) och hur du bedömer dem.")
                .modalLabel("Välj enbart de problem som påverkar patienten.")
                .collectionsLabel(
                    "Problem som påverkar patientens möjlighet att utföra sin sysselsättning:")
                .placeholder(
                    "Vad grundar sig bedömningen på? På vilka sätt och i vilken utsträckning är patienten påverkad?")
                .build()
        )
        .rules(List.of(
            CertificateElementRuleFactory.mandatory(
                QUESTION_FUNKTIONSNEDSATTNINGAR_ID,
                QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID
            ),
            CertificateElementRuleFactory.hide(
                QUESTION_SMITTBARARPENNING_ID,
                QUESTION_SMITTBARARPENNING_FIELD_ID
            )
        ))
        .validations(List.of(
            ElementValidationIcfValue.builder()
                .mandatory(true)
                .limit(4000)
                .build()
        ))
        .shouldValidate(ShouldValidateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false))
        .build();
  }

}
