package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAnamnes {

  private static final ElementId INTYGET_ANAMNES_CATEGORY_ID = new ElementId("KAT_1.2");

  private CategoryAnamnes() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAnamnes(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_ANAMNES_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Anamnesfrågor")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
