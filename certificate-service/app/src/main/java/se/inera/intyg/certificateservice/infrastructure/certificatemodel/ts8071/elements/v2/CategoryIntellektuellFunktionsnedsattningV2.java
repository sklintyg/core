package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryIntellektuellFunktionsnedsattningV2 {

  private static final ElementId CATEGORY_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID = new ElementId(
      "KAT_18");

  private CategoryIntellektuellFunktionsnedsattningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryIntellektuellFunktionsnedsattningV2(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_INTELLEKTUELL_FUNKTIONSNEDSATTNING_V2_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Intellektuell funktionsnedsättning")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}

