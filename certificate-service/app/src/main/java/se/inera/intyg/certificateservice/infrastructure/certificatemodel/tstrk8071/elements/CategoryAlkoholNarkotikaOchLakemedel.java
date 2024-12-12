package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAlkoholNarkotikaOchLakemedel {

  private static final ElementId INTYGET_ALKOHOL_NARKOTIKA_OCH_LAKEMEDEL_CATEGORY_ID = new ElementId(
      "KAT_12");

  private CategoryAlkoholNarkotikaOchLakemedel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAlkoholNarkotikaOchLakemedel(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_ALKOHOL_NARKOTIKA_OCH_LAKEMEDEL_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Alkohol, narkotika och läkemedel")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
