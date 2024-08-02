package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class MessageForutsattningarForAttLamnaSkriftligtSamtycke {

  public static ElementSpecification messageForutsattningarForAttLamnaSkriftligtSamtycke() {
    return ElementSpecification.builder()
        .id(new ElementId("forutsattningar"))
        .configuration(
            ElementConfigurationMessage.builder()
                .message(
                    ElementMessage.builder()
                        .content(
                            "Om patienten har medicinska förutsättningar att samtycka till en närståendes stöd, så ska patienten göra det. Därför ska du fylla i om hen kan samtycka eller inte.")
                        .level(ElementMessageLevel.INFO)
                        .build()
                )
                .build()
        )
        .build();
  }
}
