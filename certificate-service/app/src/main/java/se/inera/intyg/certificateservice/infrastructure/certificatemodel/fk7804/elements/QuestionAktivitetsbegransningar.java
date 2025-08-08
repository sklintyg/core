package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionAktivitetsbegransningar {

  public static final ElementId QUESTION_AKTIVITETSBEGRANSNING_ID = new ElementId(
      "17");
  private static final FieldId QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID = new FieldId("17.1");

  private QuestionAktivitetsbegransningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAktivitetsbegransningar() {
    return ElementSpecification.builder()
        .id(QUESTION_AKTIVITETSBEGRANSNING_ID)
        .configuration(
            ElementConfigurationIcf.builder()
                .id(QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID)
                .name(
                    "Ange vilken/vilka funktionsnedsättningar patienten har till följd av sjukdom och om möjligt svårighetsgrad. Ange även vad din bedömning av funktionsnedsättningar baseras på. Beskriv relevanta undersökningsfynd, testresultat, utredningssvar eller andra uppgifter (exempelvis anamnesuppgifter) och hur du bedömer dem.")
                .modalLabel("Välj enbart de svårigheter som påverkar patientens sysselsättning.")
                .collectionsLabel(
                    "Svårigheter som påverkar patientens sysselsättning:")
                .placeholder(
                    "Hur begränsar ovanstående patientens sysselsättning och i vilken utsträckning?")
                .build()
        )
        .rules(List.of(
            CertificateElementRuleFactory.mandatory(
                QUESTION_AKTIVITETSBEGRANSNING_ID,
                QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID
            ),
            CertificateElementRuleFactory.hide(
                QUESTION_SMITTBARARPENNING_ID,
                QUESTION_SMITTBARARPENNING_FIELD_ID
            )
        ))
        .build();
  }

}
