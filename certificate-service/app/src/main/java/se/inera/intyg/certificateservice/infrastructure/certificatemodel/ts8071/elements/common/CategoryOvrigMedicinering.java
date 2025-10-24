package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryOvrigMedicinering {

  private static final ElementId INTYGET_OVRIG_MEDICINERING_CATEGORY_ID = new ElementId(
      "KAT_15");

  private CategoryOvrigMedicinering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryOvrigMedicinering(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_OVRIG_MEDICINERING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Övrig medicinering")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
