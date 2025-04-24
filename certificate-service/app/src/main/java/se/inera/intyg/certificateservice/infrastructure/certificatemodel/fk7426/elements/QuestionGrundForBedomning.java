package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionGrundForBedomning {

  public static final ElementId QUESTION_GRUND_FOR_BEDOMNING_ID = new ElementId("60");
  private static final FieldId QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID = new FieldId("60.1");
  private static final short LIMIT = 4000;

  private QuestionGrundForBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGrundForBedomning() {
    return ElementSpecification.builder()
        .id(QUESTION_GRUND_FOR_BEDOMNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Varför bedömer du att barnet är allvarligt sjukt eller att det finns en stark misstanke om allvarlig diagnos?")
                .description(
                    "Beskriv varför du bedömer att barnet är allvarligt sjukt utifrån det påtagliga hotet mot barnets liv eller om det utan behandling finns fara för barnets liv. Om det påtagliga livshotet upphör men barnet fortsatt har behandling eller stor påverkan efter sjukdom så behöver du beskriva detta.")
                .id(QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_GRUND_FOR_BEDOMNING_ID,
                    QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID),
                CertificateElementRuleFactory.limit(QUESTION_GRUND_FOR_BEDOMNING_ID, LIMIT)
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

