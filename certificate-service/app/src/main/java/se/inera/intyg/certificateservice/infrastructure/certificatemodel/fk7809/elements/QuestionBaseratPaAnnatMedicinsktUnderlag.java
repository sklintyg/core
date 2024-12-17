package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionBaseratPaAnnatMedicinsktUnderlag {

  public static final ElementId QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID = new ElementId("3");
  public static final FieldId QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID = new FieldId("3.1");
  public static final PdfFieldId PDF_FIELD_TRUE = new PdfFieldId(
      "form1[0].#subform[0].ksr_JaFyll[0]");
  public static final PdfFieldId PDF_FIELD_FALSE = new PdfFieldId(
      "form1[0].#subform[0].ksr_2_1[0]");

  private QuestionBaseratPaAnnatMedicinsktUnderlag() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBaseratPaAnnatMedicinsktUnderlag() {
    return ElementSpecification.builder()
        .id(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Är utlåtandet även baserat på andra medicinska utredningar eller underlag?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID,
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID
                )
            )
        )
        .pdfConfiguration(
            PdfConfigurationBoolean.builder()
                .checkboxFalse(PDF_FIELD_FALSE)
                .checkboxTrue(PDF_FIELD_TRUE)
                .build()
        )
        .build();
  }
}