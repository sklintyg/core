package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSomnV1 {

  public static final ElementId QUESTION_SOMN_V1_ID = new ElementId("17");
  public static final FieldId QUESTION_SOMN_V1_FIELD_ID = new FieldId("17.1");

  private QuestionSomnV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSomnV1(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SOMN_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SOMN_V1_FIELD_ID)
                .name(
                    "Har personen en sömn- eller vakenhetsstörning eller symtom på sådan problematik?")
                .description(
                    "Här avses sömnapné och narkolepsi. Här avses även snarksjukdom som kan utgöra en trafiksäkerhetsrisk och annan sjukdom med sömnstörning. Insomningsbesvär som läkemedelbehandlas och inte utgör en trafiksäkerhetsrisk omfattas inte.")
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
                    QUESTION_SOMN_V1_ID,
                    QUESTION_SOMN_V1_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}

