package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAktivitetsbegransningarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_6");

  @Test
  void shallIncludeId() {
    final var element = CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Aktivitetsbegränsningar")
        .description(
            """
                Beskriv de aktivitetsbegränsningar som du bedömer att patienten har. Beskriv även om din bedömning är baserad på observationer, anamnes eller utredning gjord av någon annan. Någon annan kan till exempel vara psykolog, arbetsterapeut, audionom, syn- eller hörselpedagog.
                
                I beskrivningen kan du utgå från aktiviteter inom områden som till exempel kommunikation, förflyttning, personlig vård och hemliv. Ange om möjligt svårighetsgraden på aktivitetsbegränsningarna.
                """)
        .build();

    final var element = CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}