package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryOvrigKommentarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_16");

  @Test
  void shallIncludeId() {
    final var element = CategoryOvrigKommentar.categoryOvrigKommentar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Övrig kommentar")
        .description(
            "Övriga upplysningar som är relevanta ur trafiksäkerhetssynpunkt. Även exempelvis synpunkter/förslag om att personen bör genomgå ytterligare medicinsk utredning och orsak till detta.")
        .build();

    final var element = CategoryOvrigKommentar.categoryOvrigKommentar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}