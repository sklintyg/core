package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategorySomnOchVakenhetsstorningar {

  private static final ElementId INTYGET_SOMN_OCH_VAKENHETSSTORNINGAR_CATEGORY_ID = new ElementId(
      "KAT_11");

  private CategorySomnOchVakenhetsstorningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySomnOchVakenhetsstorningar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_SOMN_OCH_VAKENHETSSTORNINGAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Sömn- och vakenhetsstörningar")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
