package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryBedomning {

  public static final ElementId CATEGORY_BEDOMNING_ID = new ElementId("KAT_5");

  private CategoryBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryBedomning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_BEDOMNING_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Bedömning")
                .description("""
                    Utgångspunkten är att patientens arbetsförmåga ska bedömas i förhållande till patientens normala arbetstid.
                    
                    Under sjuklöneperioden kan läkaren bedöma arbetsförmågan steglöst. Det betyder att läkaren inte behöver begränsa sig till de fyra nivåer som gäller för Försäkringskassans bedömning av rätten till sjukpenning. En steglös bedömning kan innebära att patientens faktiska arbetsförmåga kan tas tillvara på ett mer optimalt sätt.
                    """)
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
