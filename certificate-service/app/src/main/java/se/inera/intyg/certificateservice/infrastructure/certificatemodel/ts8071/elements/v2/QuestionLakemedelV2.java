package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.QUESTION_MISSBRUK_V2_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionLakemedelV2 {

  public static final ElementId QUESTION_LAKEMEDEL_V2_ID = new ElementId("18.8");
  public static final FieldId QUESTION_LAKEMEDEL_V2_FIELD_ID = new FieldId("18.8");

  private QuestionLakemedelV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionLakemedelV2(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_LAKEMEDEL_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_LAKEMEDEL_V2_FIELD_ID)
                .name(
                    "Pågår läkarordinerat bruk av läkemedel som kan innebära en trafiksäkerhetsrisk?")
                .description(
                    "Här avses pågående läkarordinerat bruk av främst psykoaktiva substanser men även substanser som inte är av psykoaktivt slag men som kan påverka förmågan att framföra fordon. Ledning hittas i 12 kap. Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för körkort m.m.")
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
                    QUESTION_LAKEMEDEL_V2_ID,
                    QUESTION_LAKEMEDEL_V2_FIELD_ID
                )
            )
        )
        .mapping(
            new ElementMapping(QUESTION_MISSBRUK_V2_ID, null)
        )
        .children(List.of(children))
        .build();
  }
}