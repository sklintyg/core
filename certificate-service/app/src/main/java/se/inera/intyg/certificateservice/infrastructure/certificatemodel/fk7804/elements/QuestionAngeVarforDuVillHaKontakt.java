package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.FK7804PdfSpecification.PDF_TEXT_FIELD_ROW_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionKontakt.QUESTION_KONTAKT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionKontakt.QUESTION_KONTAKT_ID;

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

public class QuestionAngeVarforDuVillHaKontakt {

  public static final ElementId QUESTION_VARFOR_KONTAKT_ID = new ElementId(
      "26.2");
  public static final FieldId QUESTION_VARFOR_KONTAKT_FIELD_ID = new FieldId(
      "26.2");

  private QuestionAngeVarforDuVillHaKontakt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAngeVarforDuVillHaKontakt() {
    return ElementSpecification.builder()
        .id(QUESTION_VARFOR_KONTAKT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_VARFOR_KONTAKT_FIELD_ID)
                .name("Ange gärna varför du vill ha kontakt")
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(
                    new PdfFieldId("form1[0].Sida4[0].flt_txtForsakringskassanKontaktar[0]"))
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
                .maxLength(PDF_TEXT_FIELD_ROW_LENGTH * 2)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_KONTAKT_ID,
                    QUESTION_KONTAKT_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_KONTAKT_ID, true)
        )
        .includeWhenRenewing(false)
        .mapping(new ElementMapping(QUESTION_KONTAKT_ID, null))
        .build();
  }

}
