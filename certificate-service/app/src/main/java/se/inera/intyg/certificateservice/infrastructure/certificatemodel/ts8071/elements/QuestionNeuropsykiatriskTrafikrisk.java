package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatrisk.QUESTION_NEUROPSYKIATRISK_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatrisk.QUESTION_NEUROPSYKIATRISK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionNeuropsykiatriskTrafikrisk {

  public static final ElementId QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_ID = new ElementId(
      "20.2");
  public static final FieldId QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_FIELD_ID = new FieldId(
      "20.2");

  private QuestionNeuropsykiatriskTrafikrisk() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeuropsykiatriskTrafikrisk() {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_FIELD_ID)
                .name("Bedöms tillståndet utgöra en trafiksäkerhetsrisk?")
                .description(
                    "Vid denna bedömning ska störningar av impulskontroll, koncentrationsförmåga, uppmärksamhet och omdöme samt tvångsmässig fixering beaktas. Bedömningen ska göras mot bakgrund av funktionsnedsättningens konsekvenser för det dagliga livet, förekomst av beroende, missbruk eller överkonsumtion av alkohol, narkotika eller annan substans som påverkar förmågan att köra motordrivet fordon, förmåga att följa regler och förstå andras beteenden i trafiken samt kriminalitet.")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROPSYKIATRISK_ID,
                    QUESTION_NEUROPSYKIATRISK_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_ID,
                    QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_FIELD_ID
                )
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_NEUROPSYKIATRISK_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROPSYKIATRISK_ID, null)
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
