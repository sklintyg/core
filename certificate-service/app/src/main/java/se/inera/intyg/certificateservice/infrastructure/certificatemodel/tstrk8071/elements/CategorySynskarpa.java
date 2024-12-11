package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class CategorySynskarpa {

  private static final ElementId INTYGET_SYNSKARPA_CATEGORY_ID = new ElementId("KAT_1.1");

  private CategorySynskarpa() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categorySynskarpa(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(INTYGET_SYNSKARPA_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Synskärpa")
                .build()
        )
        .rules(List.of(
                CertificateElementRuleFactory.showIfNot(
                    QUESTION_SYNFUNKTIONER_ID,
                    QUESTION_SYNFUNKTIONER_FIELD_ID
                )
            )
        )
        .children(
            List.of(children)
        )
        .build();
  }
}
