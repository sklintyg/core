package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryMedicinskBehandlingTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_7");

  @Test
  void shallIncludeId() {
    final var element = CategoryMedicinskBehandling.categoryMedicinskBehandling();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Medicinsk behandling")
        .description(
            "Ange pågående och planerade medicinska behandlingar eller åtgärder som är relevanta utifrån funktionsnedsättningen. Det kan vara ordinerade läkemedel, hjälpmedel, träningsinsatser eller särskild kost. Ange den medicinska indikationen och syftet med behandlingen eller åtgärden. Du kan även beskriva andra behandlingar och åtgärder som prövats utifrån funktionsnedsättningen. Om patienten använder läkemedel som inte är subventionerade: beskriv anledningen till det.")
        .build();

    final var element = CategoryMedicinskBehandling.categoryMedicinskBehandling();

    assertEquals(expectedConfiguration, element.configuration());
  }
}