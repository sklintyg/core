package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.NUVARANDE_ARBETE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.FK7804PdfSpecification.PDF_TEXT_FIELD_ROW_LENGTH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionYrkeOchArbetsuppgifter {

  public static final ElementId QUESTION_YRKE_ARBETSUPPGIFTER_ID = new ElementId("29");
  public static final FieldId QUESTION_YRKE_ARBETSUPPGIFTER_FIELD_ID = new FieldId("29.1");

  private QuestionYrkeOchArbetsuppgifter() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionYrkeOchArbetsuppgifter() {
    return ElementSpecification.builder()
        .id(QUESTION_YRKE_ARBETSUPPGIFTER_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_YRKE_ARBETSUPPGIFTER_FIELD_ID)
                .name("Ange yrke och arbetsuppgifter")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SYSSELSATTNING_ID,
                    new RuleExpression(String.format("$%s", NUVARANDE_ARBETE.code()))
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_YRKE_ARBETSUPPGIFTER_ID,
                    QUESTION_YRKE_ARBETSUPPGIFTER_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_YRKE_ARBETSUPPGIFTER_ID,
                    (short) 4000
                ))
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.codeList(
                QUESTION_SYSSELSATTNING_ID,
                List.of(new FieldId(NUVARANDE_ARBETE.code()))
            )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtYrkeArbetsuppgifter[0]"))
                .overflowSheetFieldId(
                    new PdfFieldId("form1[0].#subform[4].flt_txtFortsattningsblad[0]"))
                .maxLength(3 * PDF_TEXT_FIELD_ROW_LENGTH)
                .build()
        )
        .build();
  }
}
