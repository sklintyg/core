package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryNeurologiskaSjukdomar {

  private static final ElementId INTYGET_NEUROLOGISKA_SJUKDOMAR_CATEGORY_ID = new ElementId(
      "KAT_7");

  private CategoryNeurologiskaSjukdomar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryNeurologiskaSjukdomar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_NEUROLOGISKA_SJUKDOMAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Neurologiska sjukdomar")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
