package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAktivitetsbegransningarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

  @Test
  void shallIncludeId() {
    final var element = categoryAktivitetsbegransningar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Aktivitetsbegränsningar")
        .description(
            """
                Beskriv de aktivitetsbegränsningar som du bedömer att barnet har och om möjligt svårighetsgraden. Beskriv även om din bedömning är baserad på observationer, anamnes eller utredning gjord av någon annan. Någon annan kan till exempel vara psykolog, arbetsterapeut eller fysioterapeut.
                
                I beskrivningen kan du utgå från aktiviteter inom områden som till exempel kommunikation, förflyttning, personlig vård och hemliv.
                """)
        .build();

    final var element = CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}