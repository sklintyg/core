package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.CertificateModelFactoryFK7427.TEXT_FIELD_LIMIT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.FK7427PdfSpecification.ROW_MAX_LENGTH;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionHalsotillstand {

  public static final ElementId QUESTION_HALSOTILLSTAND_ID = new ElementId("59");
  private static final FieldId QUESTION_HALSOTILLSTAND_FIELD_ID = new FieldId("59.1");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[2].flt_txtBeskrivBarnetsHalsotillstand[1]");

  private QuestionHalsotillstand() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHalsotillstand() {
    return ElementSpecification.builder()
        .id(QUESTION_HALSOTILLSTAND_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Beskriv barnets aktuella hälsotillstånd")
                .description(
                    "Beskriv barnets nuvarande hälsotillstånd (utifrån akut sjukdom eller försämring i funktionsnedsättning).")
                .id(QUESTION_HALSOTILLSTAND_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_HALSOTILLSTAND_ID,
                    QUESTION_HALSOTILLSTAND_FIELD_ID),
                CertificateElementRuleFactory.limit(QUESTION_HALSOTILLSTAND_ID, TEXT_FIELD_LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit((int) TEXT_FIELD_LIMIT)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .maxLength(ROW_MAX_LENGTH * 8)
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[3].flt_txtFortsattningsblad[0]"))
                .build()
        )
        .build();
  }
}