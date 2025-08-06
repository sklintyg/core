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

public class QuestionNeuropsykiatriskLakemedel {

  public static final ElementId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID = new ElementId(
      "20.4");
  public static final FieldId QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_ID = new FieldId(
      "20.4");

  private QuestionNeuropsykiatriskLakemedel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeuropsykiatriskLakemedel(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_ID)
                .name("Har personen någon läkemedelsbehandling?")
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
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID,
                    QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_FIELD_ID
                )
            )
        )
        .shouldValidate(
            ShouldValidateFactory.valueBoolean(QUESTION_NEUROPSYKIATRISK_ID)
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
        .children(List.of(children))
        .build();
  }
}
