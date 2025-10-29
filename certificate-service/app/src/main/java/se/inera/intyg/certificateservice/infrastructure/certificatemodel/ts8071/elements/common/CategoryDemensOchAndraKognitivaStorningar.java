package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryDemensOchAndraKognitivaStorningar {

  private static final ElementId INTYGET_DEMENS_OCH_ANDRA_KOGNITIVA_STORNINGAR_CATEGORY_ID = new ElementId(
      "KAT_10");

  private CategoryDemensOchAndraKognitivaStorningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryDemensOchAndraKognitivaStorningar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_DEMENS_OCH_ANDRA_KOGNITIVA_STORNINGAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Demens och andra kognitiva störningar")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
