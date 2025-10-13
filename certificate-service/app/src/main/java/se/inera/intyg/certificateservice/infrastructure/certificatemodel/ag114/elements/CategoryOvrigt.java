package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryOvrigt {

  public static final ElementId CATEGORY_OVRIGT_ID = new ElementId("KAT_6");

  private CategoryOvrigt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryOvrigt(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_OVRIGT_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Övrigt")
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
