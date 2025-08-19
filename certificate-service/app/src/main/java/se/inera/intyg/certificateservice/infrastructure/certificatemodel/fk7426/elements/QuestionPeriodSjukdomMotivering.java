package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.CertificateModelFactoryFK7426.TEXT_FIELD_LIMIT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.FK7426PdfSpecification.FORTSATTNINGSBLAD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.FK7426PdfSpecification.ROW_MAX_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdom.QUESTION_PERIOD_SJUKDOM_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPeriodSjukdomMotivering {

  public static final ElementId QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID = new ElementId("61.2");
  private static final FieldId QUESTION_PERIOD_SJUKDOM_MOTIVERING_FIELD_ID = new FieldId("61.2");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtMotiveraBedömn[0]");

  private QuestionPeriodSjukdomMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodSjukdomMotivering() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Motivera bedömningen av perioden som du anser att det finns ett påtagligt hot mot barnets liv")
                .id(QUESTION_PERIOD_SJUKDOM_MOTIVERING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID,
                    QUESTION_PERIOD_SJUKDOM_MOTIVERING_FIELD_ID),
                CertificateElementRuleFactory.limit(QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID,
                    TEXT_FIELD_LIMIT)
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
        .mapping(new ElementMapping(QUESTION_PERIOD_SJUKDOM_ID, null))
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .maxLength(ROW_MAX_LENGTH * 6)
                .overflowSheetFieldId(FORTSATTNINGSBLAD_ID)
                .build()
        )
        .includeWhenRenewing(false)
        .build();
  }
}
