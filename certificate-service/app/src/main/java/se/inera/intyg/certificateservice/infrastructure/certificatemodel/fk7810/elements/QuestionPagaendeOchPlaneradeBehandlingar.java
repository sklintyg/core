package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPagaendeOchPlaneradeBehandlingar {

  public static final ElementId QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID = new ElementId(
      "50");
  public static final FieldId QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_FIELD_ID = new FieldId(
      "50.1");

  private QuestionPagaendeOchPlaneradeBehandlingar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPagaendeOchPlaneradeBehandlingar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_FIELD_ID)
                .name(
                    "Ange pågående och planerade medicinska behandlingar")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID,
                    (short) 4000)
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
        .children(List.of(children))
        .build();
  }
}