package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryOvrigKommentarV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_16");

  @Test
  void shallIncludeId() {
    final var element = CategoryOvrigKommentarV1.categoryOvrigKommentarV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Övrig kommentar")
        .description(
            "Övriga upplysningar som är relevanta ur trafiksäkerhetssynpunkt. Även exempelvis synpunkter/förslag om att personen bör genomgå ytterligare medicinsk utredning och orsak till detta.")
        .build();

    final var element = CategoryOvrigKommentarV1.categoryOvrigKommentarV1();

    assertEquals(expectedConfiguration, element.configuration());
  }
}