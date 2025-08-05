package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryMedicinskBehandling.categoryMedicinskBehandling;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryMedicinskBehandlingTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_7");

  @Test
  void shallIncludeId() {
    final var element = categoryMedicinskBehandling();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Medicinsk behandling")
        .description(
            """
                Här beskriver du de medicinska behandlingar/åtgärder som kan påverka arbetsförmågan, vad de förväntas leda till, och en (preliminär) tidplan för åtgärderna.
                
                Om olika åtgärder behöver ske i viss ordning är det bra om du beskriver detta.
                """
        )
        .build();

    final var element = categoryMedicinskBehandling();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }
}

