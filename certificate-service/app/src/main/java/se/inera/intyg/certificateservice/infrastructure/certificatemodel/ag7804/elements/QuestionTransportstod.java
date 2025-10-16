package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionTransportstod {

  public static final ElementId QUESTION_TRANSPORTSTOD_ID = new ElementId("34");
  public static final FieldId QUESTION_TRANSPORTSTOD_FIELD_ID = new FieldId("34.1");

  private QuestionTransportstod() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionTransportstod(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_TRANSPORTSTOD_ID)
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(QUESTION_TRANSPORTSTOD_FIELD_ID)
                .name("Transport till och från arbetsplatsen")
                .description(
                    "Om patienten kan arbeta men inte kan ta sig till arbetet som vanligt kan Försäkringskassan ersätta kostnader för arbetsresor. Det innebär att patienten i stället för sjukpenning kan få ersättning för de merutgifter som uppstår för resor till och från arbetet.")
                .label(
                    "Patienten skulle kunna arbeta helt eller delvis vid hjälp med transport till och från arbetsplatsen")
                .selectedText("Ja")
                .unselectedText("Ej angivet")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.hide(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(false)
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
