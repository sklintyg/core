package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAdhdAutismPsykiskUtvecklingsstorningV1 {

  private static final ElementId INTYGET_ADHD_AUTISM_PSYKISK_UTVECKLINGSSTORNING_CATEGORY_V1_ID = new ElementId(
      "KAT_14");

  private CategoryAdhdAutismPsykiskUtvecklingsstorningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAdhdAutismPsykiskUtvecklingsstorningV1(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_ADHD_AUTISM_PSYKISK_UTVECKLINGSSTORNING_CATEGORY_V1_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name(
                    "ADHD, autismspektrumtillstånd och likartade tillstånd samt psykisk utvecklingsstörning")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
