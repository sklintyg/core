package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryEpilepsi {

  private static final ElementId INTYGET_EPILEPSI_CATEGORY_ID = new ElementId(
      "KAT_8");

  private CategoryEpilepsi() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryEpilepsi(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_EPILEPSI_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Epilepsi, epileptiskt anfall och annan medvetandestörning")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
