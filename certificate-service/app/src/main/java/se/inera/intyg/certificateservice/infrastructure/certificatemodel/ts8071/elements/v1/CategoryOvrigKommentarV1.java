package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryOvrigKommentarV1 {

  private static final ElementId INTYGET_OVRIG_KOMMENTAR_CATEGORY_V1_ID = new ElementId(
      "KAT_16");

  private CategoryOvrigKommentarV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryOvrigKommentarV1(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_OVRIG_KOMMENTAR_CATEGORY_V1_ID)
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
