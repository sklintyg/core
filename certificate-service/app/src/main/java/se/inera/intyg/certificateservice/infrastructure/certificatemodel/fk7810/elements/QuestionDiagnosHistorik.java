package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.FK3221PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.OVERFLOW_SHEET_FIELD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDiagnosHistorik {

  public static final ElementId DIAGNOSIS_MOTIVATION_ID = new ElementId("5");
  public static final FieldId DIAGNOSIS_MOTIVATION_FIELD_ID = new FieldId("5.1");
  public static final PdfFieldId DIAGNOSIS_MOTIVATION_PDF_FIELD_ID = new PdfFieldId(
      "form1[0].Sida2[0].flt_txtBeskrivRelevantHistorikDiagnos[0]");

  private QuestionDiagnosHistorik() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiagnosHistorik() {
    return ElementSpecification.builder()
        .id(DIAGNOSIS_MOTIVATION_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(DIAGNOSIS_MOTIVATION_FIELD_ID)
                .name(
                    "Beskriv kortfattat historiken för diagnoserna ovan")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    DIAGNOSIS_MOTIVATION_ID,
                    DIAGNOSIS_MOTIVATION_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    DIAGNOSIS_MOTIVATION_ID,
                    (short) 4000)
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
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(DIAGNOSIS_MOTIVATION_PDF_FIELD_ID)
                .maxLength(PDF_TEXT_FIELD_LENGTH * 4)
                .overflowSheetFieldId(OVERFLOW_SHEET_FIELD_ID)
                .build()
        )
        .build();
  }
}