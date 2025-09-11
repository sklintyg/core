package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSL.QUESTION_SJUKVARDANDE_INSATS_HSL_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfRadioOption;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSjukvardandeInsatsEgenvard {

  public static final ElementId QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID = new ElementId("70.3");
  public static final FieldId QUESTION_SJUKVARDANDE_INSATS_EGENVARD_FIELD_ID = new FieldId("70.3");
  private static final PdfFieldId PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[5].RadioButtonListModul8_2[0]");
  private static final PdfRadioOption PDF__OPTION_TRUE = new PdfRadioOption("2");
  private static final PdfRadioOption PDF_OPTION_FALSE = new PdfRadioOption("1");

  private QuestionSjukvardandeInsatsEgenvard() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukvardandeInsatsEgenvard(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SJUKVARDANDE_INSATS_EGENVARD_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har patienten behov av hjälp som bedöms kunna utföras som egenvård?")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID,
                    QUESTION_SJUKVARDANDE_INSATS_EGENVARD_FIELD_ID)
            )
        )
        .mapping(new ElementMapping(QUESTION_SJUKVARDANDE_INSATS_HSL_ID, null))
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationRadioBoolean.builder()
                .pdfFieldId(PDF_FIELD_ID)
                .optionTrue(PDF__OPTION_TRUE)
                .optionFalse(PDF_OPTION_FALSE)
                .build()
        )
        .children(List.of(children))
        .build();
  }
}