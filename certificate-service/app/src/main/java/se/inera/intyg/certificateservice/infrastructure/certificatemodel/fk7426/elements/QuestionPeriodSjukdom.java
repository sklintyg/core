package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPeriodSjukdom {

  public static final ElementId QUESTION_PERIOD_SJUKDOM_ID = new ElementId("61");
  public static final FieldId QUESTION_PERIOD_SJUKDOM_FIELD_ID = new FieldId("61.1");
  private static final PdfFieldId PDF_FIELD_ID_FROM = new PdfFieldId(
      "form1[0].#subform[3].flt_datumFranMed[0]");
  private static final PdfFieldId PDF_FIELD_ID_TO = new PdfFieldId(
      "form1[0].#subform[3].flt_datumTillMed[0]");

  private QuestionPeriodSjukdom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodSjukdom() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_SJUKDOM_ID)
        .configuration(
            ElementConfigurationDateRange.builder()
                .name("För vilken period bedömer du att barnet är allvarligt sjuk?")
                .labelFrom("Fr.o.m")
                .labelTo("T.o.m")
                .id(QUESTION_PERIOD_SJUKDOM_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_PERIOD_SJUKDOM_ID,
                    QUESTION_PERIOD_SJUKDOM_FIELD_ID)
            )
        )
        .validations(
            List.of(
                ElementValidationDateRange.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationDateRange.builder()
                .from(PDF_FIELD_ID_FROM)
                .to(PDF_FIELD_ID_TO)
                .build()
        )
        .includeWhenRenewing(false)
        .build();
  }
}
