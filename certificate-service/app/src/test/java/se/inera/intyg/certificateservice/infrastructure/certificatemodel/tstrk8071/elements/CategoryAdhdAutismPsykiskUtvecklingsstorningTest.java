package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAdhdAutismPsykiskUtvecklingsstorningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_14");

  @Test
  void shallIncludeId() {
    final var element = CategoryAdhdAutismPsykiskUtvecklingsstorning.categoryAdhdAutismPsykiskUtvecklingsstorning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name(
            "ADHD, autismspektrumtillstånd och likartade tillstånd samt psykisk utvecklingsstörning")
        .build();

    final var element = CategoryAdhdAutismPsykiskUtvecklingsstorning.categoryAdhdAutismPsykiskUtvecklingsstorning();

    assertEquals(expectedConfiguration, element.configuration());
  }
}