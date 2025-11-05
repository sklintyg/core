package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryOvrigKommentarV2 {

  private static final ElementId INTYGET_OVRIG_KOMMENTAR_V2_CATEGORY_ID = new ElementId(
      "KAT_16");

  private CategoryOvrigKommentarV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryOvrigKommentarV2(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_OVRIG_KOMMENTAR_V2_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Övrig kommentar")
                .description(
                    "Övriga upplysningar som är relevanta ur trafiksäkerhetssynpunkt. Exempelvis synpunkter eller förslag om att personen bör genomgå ytterligare medicinsk utredning och orsak till detta.")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}

