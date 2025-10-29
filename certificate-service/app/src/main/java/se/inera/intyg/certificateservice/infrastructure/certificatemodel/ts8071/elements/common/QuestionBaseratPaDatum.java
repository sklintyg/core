package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg.DISTANSKONTAKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg.UNDERSOKNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBaseratPa.QUESTION_BASERAT_PA_ID;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionBaseratPaDatum {

  public static final ElementId QUESTION_BASERAT_PA_DATUM_ID = new ElementId(
      "2.2");
  private static final FieldId QUESTION_BASERAT_PA_DATUM_FIELD_ID = new FieldId(
      "2.2");

  private QuestionBaseratPaDatum() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBaseratPaDatum() {
    return ElementSpecification.builder()
        .id(QUESTION_BASERAT_PA_DATUM_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .id(QUESTION_BASERAT_PA_DATUM_FIELD_ID)
                .name("Datum för undersökning/kontakt")
                .max(Period.ofDays(0))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_BASERAT_PA_DATUM_ID,
                    QUESTION_BASERAT_PA_DATUM_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_BASERAT_PA_ID,
                    new RuleExpression(
                        String.format("exists(%s) || exists(%s)",
                            DISTANSKONTAKT.code(), UNDERSOKNING.code())
                    )
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.codes(
                QUESTION_BASERAT_PA_ID,
                List.of(new FieldId(DISTANSKONTAKT.code()), new FieldId(UNDERSOKNING.code())))
        )
        .mapping(new ElementMapping(QUESTION_BASERAT_PA_ID, null))
        .build();
  }
}
