package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryPrognos {

  private static final ElementId PROGNOS_CATEGORY_ID = new ElementId("KAT_7");

  private CategoryPrognos() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryPrognos(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(PROGNOS_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Prognos")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}