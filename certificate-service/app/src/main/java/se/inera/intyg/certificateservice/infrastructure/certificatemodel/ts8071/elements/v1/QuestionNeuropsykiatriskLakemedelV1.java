package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskV1.QUESTION_NEUROPSYKIATRISK_FIELD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskV1.QUESTION_NEUROPSYKIATRISK_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionNeuropsykiatriskLakemedelV1 {

  public static final ElementId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID = new ElementId(
      "20.4");
  public static final FieldId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_V1_ID = new FieldId(
      "20.4");

  private QuestionNeuropsykiatriskLakemedelV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeuropsykiatriskLakemedelV1(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_V1_ID)
                .name("Har personen någon läkemedelsbehandling?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROPSYKIATRISK_V1_ID,
                    QUESTION_NEUROPSYKIATRISK_FIELD_V1_ID
                ),
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID,
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_V1_ID
                )
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_NEUROPSYKIATRISK_V1_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROPSYKIATRISK_V1_ID, null)
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}
