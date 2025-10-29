package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryIntygetAvser {

  private static final ElementId INTYGET_AVSER_CATEGORY_ID = new ElementId("KAT_0.0");

  private CategoryIntygetAvser() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryIntygetAvser(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_AVSER_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Intyget avser")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
