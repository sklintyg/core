package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class MessageArbetsformagaAlert {

  public static final ElementId MESSAGE_ARBETSFORMAGA_ALERT_ID = new ElementId(
      "messageArbetsformaga");

  private MessageArbetsformagaAlert() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification messageArbetsformagaAlert() {
    return ElementSpecification.builder()
        .id(MESSAGE_ARBETSFORMAGA_ALERT_ID)
        .configuration(
            ElementConfigurationMessage.builder()
                .message(
                    ElementMessage.builder()
                        .content(
                            "Om patienten bedöms vara arbetsoförmögen i mer än 14 dagar ska Läkarintyg om arbetsförmåga – arbetsgivaren (AG7804) användas. Intyget skapas från Försäkringskassans läkarintyg för sjukpenning (FK7804) och innehåller motsvarande information.")
                        .level(MessageLevel.INFO)
                        .build()
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_PERIOD_BEDOMNING_ID,
                    new RuleExpression(
                        String.format("($%s.to - $%s.from) >= 14",
                            QUESTION_PERIOD_BEDOMNING_FIELD_ID.value(),
                            QUESTION_PERIOD_BEDOMNING_FIELD_ID.value()))
                )
            )
        )
        .build();
  }
}
