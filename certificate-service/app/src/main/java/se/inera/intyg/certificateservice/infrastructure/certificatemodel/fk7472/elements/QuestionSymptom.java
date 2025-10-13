package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSymptom {

  public static final ElementId QUESTION_SYMPTOM_ID = new ElementId("55");
  public static final FieldId QUESTION_SYMPTOM_FIELD_ID = new FieldId("55.1");
  private static final PdfFieldId PDF_SYMPTOM_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiagnos[0]");
  private static final short LIMIT = 318;

  private QuestionSymptom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSymptom() {
    return ElementSpecification.builder()
        .id(QUESTION_SYMPTOM_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Ange diagnos eller symtom")
                .id(QUESTION_SYMPTOM_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SYMPTOM_ID,
                    QUESTION_SYMPTOM_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(QUESTION_SYMPTOM_ID, LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(318)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_SYMPTOM_FIELD_ID)
                .build()
        )
        .build();
  }
}
