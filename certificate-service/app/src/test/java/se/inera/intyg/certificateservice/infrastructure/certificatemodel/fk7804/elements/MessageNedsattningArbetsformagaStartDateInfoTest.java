package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class MessageNedsattningArbetsformagaStartDateInfoTest {

  private static final ElementId ELEMENT_ID = new ElementId("earlyStartDate");

  @Test
  void shallIncludeId() {
    final var element = MessageNedsattningArbetsformagaStartDateInfo.messageStartDateInfo();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationMessage.builder()
        .message(
            ElementMessage.builder()
                .content(
                    "Du har angett att sjukskrivningsperioden startar mer än 7 dagar bakåt i tiden. Ange orsaken till detta i fältet \"Övriga upplysningar\".")
                .level(MessageLevel.OBSERVE)
                .build()
        )
        .build();
    final var element = MessageNedsattningArbetsformagaStartDateInfo.messageStartDateInfo();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeShowRuleWithCorrectExpression() {
    final var element = MessageNedsattningArbetsformagaStartDateInfo.messageStartDateInfo();
    final var rules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.SHOW)
            .id(new ElementId("32"))
            .expression(
                new RuleExpression(
                    "($EN_FJARDEDEL.from < -7) || ($HALFTEN.from < -7) || ($TRE_FJARDEDEL.from < -7) || ($HELT_NEDSATT.from < -7)"
                ))
            .build()
    );

    assertEquals(rules, element.rules());
  }
}

