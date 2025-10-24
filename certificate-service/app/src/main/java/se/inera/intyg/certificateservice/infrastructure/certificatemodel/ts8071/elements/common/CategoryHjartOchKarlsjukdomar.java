package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryHjartOchKarlsjukdomar {

  private static final ElementId INTYGET_HJART_OCH_KARLSJUKDOMAR_CATEGORY_ID = new ElementId(
      "KAT_5");

  private CategoryHjartOchKarlsjukdomar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryHjartOchKarlsjukdomar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_HJART_OCH_KARLSJUKDOMAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Hjärt- och kärlsjukdomar")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
