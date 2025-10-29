package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class CategoryHorselV2 {

  private static final ElementId CATEGORY_HORSEL_V2_ID = new ElementId("KAT_3");

  private CategoryHorselV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryHorselV2(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(CATEGORY_HORSEL_V2_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Hörsel")
                .build()
        )
        .children(List.of(children))
        .rules(
            List.of(
                CertificateElementRuleFactory.showOrExist(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(
                        new FieldId(GR_II_III.code()),
                        new FieldId(FORLANG_GR_II_III.code()),
                        new FieldId(TAXI.code())
                    )
                )
            )
        )
        .build();
  }
}

