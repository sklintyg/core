package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryFunktionsnedsattningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_4");

  @Test
  void shallIncludeId() {
    final var element = categoryFunktionsnedsattning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Funktionsnedsättning")
        .description(
            """
                Utifrån diagnoserna ovan, beskriv eventuell funktionsnedsättning för respektive funktionsområde samt gradering till exempel enligt internationell klassifikation av funktionstillstånd, funktionshinder och hälsa (ICF) (lätt, måttlig, stor eller total).
                
                Basera beskrivningen på vad som framkommit vid senaste undersökningstillfället och tidigare utredningar. Ange vilka status- och undersökningsfynd du baserar bedömningen på."""
        )
        .build();

    final var element = categoryFunktionsnedsattning();

    assertEquals(expectedConfiguration, element.configuration());
  }
}