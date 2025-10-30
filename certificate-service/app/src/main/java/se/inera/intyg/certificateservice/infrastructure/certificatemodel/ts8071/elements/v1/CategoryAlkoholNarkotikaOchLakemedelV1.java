package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAlkoholNarkotikaOchLakemedelV1 {

  private static final ElementId INTYGET_ALKOHOL_NARKOTIKA_OCH_LAKEMEDEL_CATEGORY_V1_ID = new ElementId(
      "KAT_12");

  private CategoryAlkoholNarkotikaOchLakemedelV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAlkoholNarkotikaOchLakemedelV1(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_ALKOHOL_NARKOTIKA_OCH_LAKEMEDEL_CATEGORY_V1_ID)
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
