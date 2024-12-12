package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryOvrigKommentar {

  private static final ElementId INTYGET_OVRIG_KOMMENTAR_CATEGORY_ID = new ElementId(
      "KAT_16");

  private CategoryOvrigKommentar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryOvrigKommentar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_OVRIG_KOMMENTAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Övrig kommentar")
                .description(
                    "Övriga upplysningar som är relevanta ur trafiksäkerhetssynpunkt. Även exempelvis synpunkter/förslag om att personen bör genomgå ytterligare medicinsk utredning och orsak till detta.")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
