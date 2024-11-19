package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;

class MessageForutsattningarForAttLamnaSkriftligtSamtyckeTest {

  private static final ElementId ELEMENT_ID = new ElementId("forutsattningar");

  @Test
  void shallIncludeId() {
    final var element = MessageForutsattningarForAttLamnaSkriftligtSamtycke.messageForutsattningarForAttLamnaSkriftligtSamtycke();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationMessage.builder()
        .message(
            ElementMessage.builder()
                .content(
                    "Om patienten har medicinska förutsättningar att samtycka till en närståendes stöd, så ska patienten göra det. Därför ska du fylla i om hen kan samtycka eller inte.")
                .level(MessageLevel.INFO)
                .build()
        )
        .build();

    final var element = MessageForutsattningarForAttLamnaSkriftligtSamtycke.messageForutsattningarForAttLamnaSkriftligtSamtycke();

    assertEquals(expectedConfiguration, element.configuration());
  }
}