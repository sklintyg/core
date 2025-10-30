package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPsykiskV2 {

  public static final ElementId QUESTION_PSYKISK_V2_ID = new ElementId("19");
  public static final FieldId QUESTION_PSYKISK_V2_FIELD_ID = new FieldId("19.1");

  private QuestionPsykiskV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPsykiskV2(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_PSYKISK_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_PSYKISK_V2_FIELD_ID)
                .name("Har personen eller har personen haft psykisk sjukdom eller störning?")
                .description(
                    "Här avses sjukdomar och störningar som kan påverka beteendet, så att det kan utgöra en trafiksäkerhetsrisk. Med sjukdomar avses exempelvis schizofreni, annan psykos eller affektiva syndrom såsom bipolär sjukdom. Med störningar avses exempelvis olika personlighetsstörningar såsom paranoid, antisocial, narcissistisk eller emotionellt instabil personlighetsstörning och schizotyp personlighetsstörning.\n\n"
                        + "I normalfallet medför paniksyndrom, utmattningssyndrom, ångest (PTSD), generaliserat ångestsyndrom (GAD), årstidsbundna depressioner inte en trafiksäkerhetsrisk och behöver i sådant fall inte anges.")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_PSYKISK_V2_ID,
                    QUESTION_PSYKISK_V2_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}

