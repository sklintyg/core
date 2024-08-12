package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.elements;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionBeraknatFodelsedatum {

  public static final ElementId QUESTION_BERAKNAT_FODELSEDATUM_ID = new ElementId("54");
  public static final FieldId QUESTION_BERAKNAT_FODELSEDATUM_FIELD_ID = new FieldId("54.1");
  private static final PdfFieldId PDF_FODELSEDATUM_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_dat[0]");

  private QuestionBeraknatFodelsedatum() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBeraknatFodelsedatum() {
    return ElementSpecification.builder()
        .id(QUESTION_BERAKNAT_FODELSEDATUM_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .name("Datum")
                .id(QUESTION_BERAKNAT_FODELSEDATUM_FIELD_ID)
                .min(Period.ofDays(0))
                .max(Period.ofYears(1))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_BERAKNAT_FODELSEDATUM_ID,
                    QUESTION_BERAKNAT_FODELSEDATUM_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .min(Period.ofDays(0))
                    .max(Period.ofYears(1))
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationDate.builder()
                .pdfFieldId(PDF_FODELSEDATUM_FIELD_ID)
                .build()
        )
        .build();
  }
}
