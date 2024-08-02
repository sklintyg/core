package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008;

public class QuestionPeriod {

  public static final ElementId QUESTION_PERIOD_ID = new ElementId("56");
  private static final String QUESTION_PERIOD_FIELD_ID = "56.1";
  private static final PdfFieldId PDF_PERIOD_FIELD_ID_PREFIX = new PdfFieldId(
      "form1[0].#subform[0]");

  public static ElementSpecification questionPeriod() {
    final var dateRanges = List.of(
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0008.EN_ATTONDEL.code()),
            "12,5 procent",
            CodeSystemKvFkmu0008.EN_ATTONDEL
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0008.EN_FJARDEDEL.code()),
            "25 procent",
            CodeSystemKvFkmu0008.EN_FJARDEDEL
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0008.HALVA.code()),
            "50 procent",
            CodeSystemKvFkmu0008.HALVA
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0008.TRE_FJARDEDELAR.code()),
            "75 procent",
            CodeSystemKvFkmu0008.TRE_FJARDEDELAR
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0008.HELA.code()),
            "100 procent",
            CodeSystemKvFkmu0008.HELA
        )
    );

    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationCheckboxDateRangeList.builder()
                .name("Jag bedömer att barnet inte ska vårdas i ordinarie tillsynsform")
                .label("Andel av ordinarie tid:")
                .id(new FieldId(QUESTION_PERIOD_FIELD_ID))
                .dateRanges(dateRanges)
                .min(Period.ofMonths(-1))
                .hideWorkingHours(true)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PERIOD_ID,
                    dateRanges.stream().map(ElementConfigurationCode::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDateRangeList.builder()
                    .min(Period.ofMonths(-1))
                    .mandatory(true)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationDateRangeList.builder()
                .pdfFieldId(PDF_PERIOD_FIELD_ID_PREFIX)
                .build()
        )
        .build();
  }
}
