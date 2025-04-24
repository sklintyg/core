package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

import java.util.List;

public class QuestionPlaneradeBehandlingar {

  public static final ElementId QUESTION_PLANERADE_BEHANDLING_ID = new ElementId("20");
  private static final FieldId QUESTION_PLANERADE_BEHANDLING_FIELD_ID = new FieldId(
      "20.1");
  private static final short LIMIT = 4000;

  private QuestionPlaneradeBehandlingar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPlaneradeBehandlingar() {
    return ElementSpecification.builder()
        .id(QUESTION_PLANERADE_BEHANDLING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Planerade medicinska behandlingar/åtgärder")
                    .description("Ange vad syftet är och om möjligt tidplan samt ansvarig vårdenhet.")
                .id(QUESTION_PLANERADE_BEHANDLING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_PLANERADE_BEHANDLING_ID,
                    LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }
}
