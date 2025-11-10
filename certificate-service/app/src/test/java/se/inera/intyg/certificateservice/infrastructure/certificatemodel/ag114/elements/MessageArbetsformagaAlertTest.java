package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.MessageArbetsformagaAlert.messageArbetsformagaAlert;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class MessageArbetsformagaAlertTest {

  private static final ElementId ELEMENT_ID = new ElementId("messageArbetsformaga");

  @Test
  void shouldIncludeId() {
    final var element = messageArbetsformagaAlert();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationMessage.builder()
        .message(
            ElementMessage.builder()
                .content(
                    "Om patienten bedöms vara arbetsoförmögen i mer än 14 dagar ska Läkarintyg om arbetsförmåga – arbetsgivaren (AG7804) användas. Intyget skapas från Försäkringskassans läkarintyg för sjukpenning (FK7804) och innehåller motsvarande information.")
                .level(MessageLevel.INFO)
                .build()
        )
        .build();
    final var element = messageArbetsformagaAlert();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeShowRuleWithCorrectExpression() {
    final var element = messageArbetsformagaAlert();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.SHOW)
            .id(QUESTION_PERIOD_BEDOMNING_ID)
            .expression(new RuleExpression("($7.2.to - $7.2.from) >= 14"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }
}
