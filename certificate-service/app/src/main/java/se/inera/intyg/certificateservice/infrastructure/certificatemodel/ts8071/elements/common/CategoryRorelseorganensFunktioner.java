package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryRorelseorganensFunktioner {

  private static final ElementId INTYGET_RORELSEORGANENS_FUNKTIONER_CATEGORY_ID = new ElementId(
      "KAT_4");

  private CategoryRorelseorganensFunktioner() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryRorelseorganensFunktioner(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_RORELSEORGANENS_FUNKTIONER_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Rörelseorganens funktioner")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
