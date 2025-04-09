package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSymtom {

  public static final ElementId QUESTION_SYMTOM_ID = new ElementId("55");
  private static final FieldId QUESTION_SYMTOM_FIELD_ID = new FieldId("55.1");
  private static final short LIMIT = 4000;

  private QuestionSymtom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSymtom() {
    return ElementSpecification.builder()
        .id(QUESTION_SYMTOM_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Fyll i vilka symtom barnet har om diagnos inte är fastställd")
                .id(QUESTION_SYMTOM_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_SYMTOM_ID, LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }
}
