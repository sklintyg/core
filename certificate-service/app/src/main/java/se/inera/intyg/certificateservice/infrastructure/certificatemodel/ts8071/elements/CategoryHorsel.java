package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.FORLANG_GR_II;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.GR_II;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class CategoryHorsel {

  private static final ElementId INTYGET_HORSEL_CATEGORY_ID = new ElementId("KAT_3");

  private CategoryHorsel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryHorsel(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_HORSEL_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Hörsel")
                .build()
        )
        .children(List.of(children))
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_INTYGET_AVSER_ID,
                    new RuleExpression(
                        String.format(
                            "!exists(%s) && !exists(%s)", GR_II.code(), FORLANG_GR_II.code()
                        )
                    )
                )
            )
        )
        .build();
  }
}
