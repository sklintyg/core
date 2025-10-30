package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySynfunktion {

  private static final ElementId INTYGET_SYNFUNKTION_CATEGORY_ID = new ElementId("KAT_1.0");

  private CategorySynfunktion() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySynfunktion(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_SYNFUNKTION_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Synfunktioner")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
