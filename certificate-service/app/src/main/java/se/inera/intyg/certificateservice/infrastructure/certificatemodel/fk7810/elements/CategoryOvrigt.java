package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryOvrigt {

  private static final ElementId OVRIGT_CATEGORY_ID = new ElementId("KAT_9");

  private CategoryOvrigt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryOvrigt(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(OVRIGT_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Övrigt")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
