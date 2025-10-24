package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryPsykiskaSjukdomarOchStorningar {

  private static final ElementId INTYGET_PSYKISKA_SJUKDOMAR_OCH_STORNINGAR_CATEGORY_ID = new ElementId(
      "KAT_13");

  private CategoryPsykiskaSjukdomarOchStorningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryPsykiskaSjukdomarOchStorningar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_PSYKISKA_SJUKDOMAR_OCH_STORNINGAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Psykiska sjukdomar och störningar")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
