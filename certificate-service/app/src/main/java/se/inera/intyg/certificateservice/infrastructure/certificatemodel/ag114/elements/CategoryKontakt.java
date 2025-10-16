package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryKontakt {

  public static final ElementId CATEGORY_KONTAKT_ID = new ElementId("KAT_7");

  private CategoryKontakt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryKontakt(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_KONTAKT_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Kontakt")
                .build()
        )
        .children(List.of(children))
        .build();
  }
}
