package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryBalanssinne {

  private static final ElementId INTYGET_BALANSSINNE_CATEGORY_ID = new ElementId("KAT_2");

  private CategoryBalanssinne() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryBalanssinne(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_BALANSSINNE_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Balanssinne")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
