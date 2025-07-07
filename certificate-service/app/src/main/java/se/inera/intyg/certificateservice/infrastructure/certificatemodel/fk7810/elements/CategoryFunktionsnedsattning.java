package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryFunktionsnedsattning {

  private static final ElementId FUNKTIONSNEDSATTNING_CATEGORY_ID = new ElementId("KAT_4");

  private CategoryFunktionsnedsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryFunktionsnedsattning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(FUNKTIONSNEDSATTNING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Funktionsnedsättning")
                .description(
                    """
                        Utifrån diagnoserna ovan, beskriv eventuell funktionsnedsättning för respektive funktionsområde samt gradering till exempel enligt internationell klassifikation av funktionstillstånd, funktionshinder och hälsa (ICF) (lätt, måttlig, stor eller total).
                        
                        Basera beskrivningen på vad som framkommit vid senaste undersökningstillfället och tidigare utredningar. Ange vilka status- och undersökningsfynd du baserar bedömningen på."""
                )
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}