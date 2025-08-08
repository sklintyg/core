package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategorySmittbararpenning.categorySmittbararpenning;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategorySmittbararpenningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

  @Test
  void shallIncludeId() {
    final var element = categorySmittbararpenning();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Smittbärarpenning")
        .description(
            """
                Fylls i om patienten kan arbeta men inte får göra det på grund av något av följande:
                <ul><li>Att patienten har en allmänfarlig sjukdom och en läkare har beslutat om förhållningsregler enligt smittskyddslagen.</li><li>Att patienten genomgår en läkarundersökning eller hälsokontroll i syfte att klarlägga om hen är smittad av en allmänfarlig sjukdom.</li><li>Att patienten har en sjukdom, en smitta, eller ett sår som gör att hen inte får hantera livsmedel.</li></ul>
                Ange vilken allmänfarlig sjukdom som patienten har eller kan antas ha i fältet för diagnos. Ange sedan för vilken period som förhållningsreglerna gäller i fältet för nedsättning av arbetsförmåga.
                """
        )
        .build();

    final var element = categorySmittbararpenning();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }
}

