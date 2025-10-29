package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSomnBehandling {

  public static final ElementId QUESTION_SOMN_BEHANDLING_ID = new ElementId("17.3");
  public static final FieldId QUESTION_SOMN_BEHANDLING_FIELD_ID = new FieldId("17.3");

  private QuestionSomnBehandling() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSomnBehandling(ElementId parentElementId) {
    return ElementSpecification.builder()
        .id(QUESTION_SOMN_BEHANDLING_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SOMN_BEHANDLING_FIELD_ID)
                .name(
                    "Förekommer behandling mot sömn- och vakenhetsstörning?")
                .description(
                    "Här avses behandling för sömnapné med så väl bettskena eller annat hjälpmedel för andning såsom exempelvis CPAP, BiPAP eller APAP. Här avses även läkemedel för narkolepsi eller narkotikaklassade läkemedel för annan sömn- eller vakenhetsstörning.")
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
                    QUESTION_SOMN_BEHANDLING_ID,
                    QUESTION_SOMN_BEHANDLING_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(parentElementId, null))
        .build();
  }
}