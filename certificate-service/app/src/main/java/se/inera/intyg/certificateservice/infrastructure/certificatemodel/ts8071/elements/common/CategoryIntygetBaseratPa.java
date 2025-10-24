package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryIntygetBaseratPa {

  private static final ElementId INTYGET_BASERAT_PA_CATEGORY_ID = new ElementId("KAT_0.1");

  private CategoryIntygetBaseratPa() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryIntygetBaseratPa(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_BASERAT_PA_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Intyget är baserat på")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
