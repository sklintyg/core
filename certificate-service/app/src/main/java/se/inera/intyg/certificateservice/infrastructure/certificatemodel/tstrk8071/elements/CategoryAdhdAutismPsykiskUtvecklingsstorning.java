package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryAdhdAutismPsykiskUtvecklingsstorning {

  private static final ElementId INTYGET_ADHD_AUTISM_PSYKISK_UTVECKLINGSSTORNING_CATEGORY_ID = new ElementId(
      "KAT_14");

  private CategoryAdhdAutismPsykiskUtvecklingsstorning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryAdhdAutismPsykiskUtvecklingsstorning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_ADHD_AUTISM_PSYKISK_UTVECKLINGSSTORNING_CATEGORY_ID)
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
