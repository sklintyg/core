package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryHalsotillstand {

  private static final ElementId HALSOTILLSTAND_CATEGORY_ID = new ElementId("KAT_3");

  private CategoryHalsotillstand() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryHalsotillstand(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(HALSOTILLSTAND_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Barnets hälsotillstånd")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
