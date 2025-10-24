package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAdhdAutismPsykiskUtvecklingsstorningV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_14");

  @Test
  void shallIncludeId() {
    final var element = CategoryAdhdAutismPsykiskUtvecklingsstorningV1.categoryAdhdAutismPsykiskUtvecklingsstorningV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name(
            "ADHD, autismspektrumtillstånd och likartade tillstånd samt psykisk utvecklingsstörning")
        .build();

    final var element = CategoryAdhdAutismPsykiskUtvecklingsstorningV1.categoryAdhdAutismPsykiskUtvecklingsstorningV1();

    assertEquals(expectedConfiguration, element.configuration());
  }
}