package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryHorsel {

  private static final ElementId INTYGET_HORSEL_CATEGORY_ID = new ElementId("KAT_3");

  private CategoryHorsel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryHorsel(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_HORSEL_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Hörsel")
                .build()
        )
        .children(List.of(children))
        // TODO: Add rules for show if specific group in q1
        .build();
  }
}
