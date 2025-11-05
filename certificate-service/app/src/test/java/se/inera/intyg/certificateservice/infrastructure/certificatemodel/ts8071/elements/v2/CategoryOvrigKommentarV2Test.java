package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryOvrigKommentarV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_16");

  @Test
  void shouldIncludeId() {
    final var element = CategoryOvrigKommentarV2.categoryOvrigKommentarV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Övrig kommentar")
        .description(
            "Övriga upplysningar som är relevanta ur trafiksäkerhetssynpunkt. Exempelvis synpunkter eller förslag om att personen bör genomgå ytterligare medicinsk utredning och orsak till detta.")
        .build();

    final var element = CategoryOvrigKommentarV2.categoryOvrigKommentarV2();

    assertEquals(expectedConfiguration, element.configuration());
  }
}

