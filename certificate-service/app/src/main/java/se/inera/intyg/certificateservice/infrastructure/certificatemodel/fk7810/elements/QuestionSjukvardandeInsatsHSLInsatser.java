package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.FK3221PdfSpecification.PDF_TEXT_FIELD_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.OVERFLOW_SHEET_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSL.QUESTION_SJUKVARDANDE_INSATS_HSL_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSL.QUESTION_SJUKVARDANDE_INSATS_HSL_ID;

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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionSjukvardandeInsatsHSLInsatser {

  public static final ElementId QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_ID = new ElementId(
      "70.2");
  private static final FieldId QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_FIELD_ID = new FieldId(
      "70.2");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[5].flt_txtVilkaInsatserOmfattning[0]");

  private QuestionSjukvardandeInsatsHSLInsatser() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukvardandeInsatsHSLInsatser() {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_FIELD_ID)
                .name("Ange vilka insatser och i vilken omfattning")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_ID,
                    QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_FIELD_ID),
                CertificateElementRuleFactory.limit(
                    QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_SJUKVARDANDE_INSATS_HSL_ID,
                    QUESTION_SJUKVARDANDE_INSATS_HSL_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_SJUKVARDANDE_INSATS_HSL_ID, null))
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_SJUKVARDANDE_INSATS_HSL_ID))
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .maxLength(PDF_TEXT_FIELD_LENGTH * 3)
                .overflowSheetFieldId(OVERFLOW_SHEET_FIELD_ID)
                .build()
        )
        .build();
  }
}
