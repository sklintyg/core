package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionVardEllerTillsyn {

  public static final ElementId QUESTION_VARD_ELLER_TILLSYN_ID = new ElementId("62");
  private static final FieldId QUESTION_VARD_ELLER_TILLSYN_FIELD_ID = new FieldId("62.5");
  private static final short LIMIT = 4000;

  private QuestionVardEllerTillsyn() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionVardEllerTillsyn() {
    return ElementSpecification.builder()
        .id(QUESTION_VARD_ELLER_TILLSYN_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Beskriv barnets behov av vård eller tillsyn")
                .description(
                    "Beskriv vilken vård eller tillsyn som barnet behöver av förälder samt omfattning av denna vård eller tillsyn.")
                .id(QUESTION_VARD_ELLER_TILLSYN_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_VARD_ELLER_TILLSYN_ID,
                    QUESTION_VARD_ELLER_TILLSYN_FIELD_ID),
                CertificateElementRuleFactory.limit(QUESTION_VARD_ELLER_TILLSYN_ID, LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }
}
