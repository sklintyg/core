package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAlkoholOchLakemedelV2 {

  private static final ElementId CATEGORY_ALKOHOL_OCH_LAKEMEDEL_V2_ID = new ElementId(
      "KAT_12");

  private CategoryAlkoholOchLakemedelV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAlkoholOchLakemedelV2(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_ALKOHOL_OCH_LAKEMEDEL_V2_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Alkohol, andra psykoaktiva substanser och läkemedel")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
