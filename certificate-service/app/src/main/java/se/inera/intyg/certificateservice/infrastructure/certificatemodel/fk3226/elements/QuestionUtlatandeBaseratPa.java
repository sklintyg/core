package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import java.time.Period;
import java.util.List;
import java.util.Map;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateCheckbox;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;

public class QuestionUtlatandeBaseratPa {

  public static final ElementId QUESTION_UTLATANDE_BASERAT_PA_ID = new ElementId("1");
  public static final FieldId QUESTION_UTLATANDE_BASERAT_PA_FIELD_ID = new FieldId("1.1");
  public static final String UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID = "undersokningAvPatienten";
  public static final String UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID = "journaluppgifter";
  public static final FieldId UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("annat");
  private static final PdfFieldId PDF_STATEMENT_BASED_ON_INVESTIGATION_CHECKBOX_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_UndersokningPatient[0]");
  private static final PdfFieldId PDF_STATEMENT_BASED_ON_JOURNAL_CHECKBOX_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_Journaluppgifter[0]");
  private static final PdfFieldId PDF_STATEMENT_BASED_ON_OTHER_CHECKBOX_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_Annat[0]");
  private static final PdfFieldId PDF_STATEMENT_BASED_ON_INVESTIGATION_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUl_1[0]");
  private static final PdfFieldId PDF_STATEMENT_BASED_ON_JOURNAL_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUl_2[0]");
  private static final PdfFieldId PDF_STATEMENT_BASED_ON_OTHER_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUl_3[0]");

  private QuestionUtlatandeBaseratPa() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionUtlatandeBaseratPa(ElementSpecification... children) {
    final var checkboxDates = List.of(
        CheckboxDate.builder()
            .id(new FieldId(UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID))
            .label(CodeSystemKvFkmu0001.UNDERSOKNING.displayName())
            .code(CodeSystemKvFkmu0001.UNDERSOKNING)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID))
            .label(CodeSystemKvFkmu0001.JOURNALUPPGIFTER.displayName())
            .code(CodeSystemKvFkmu0001.JOURNALUPPGIFTER)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID)
            .label(CodeSystemKvFkmu0001.ANNAT.displayName())
            .code(CodeSystemKvFkmu0001.ANNAT)
            .min(null)
            .max(Period.ofDays(0))
            .build()
    );

    return ElementSpecification.builder()
        .id(QUESTION_UTLATANDE_BASERAT_PA_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .id(QUESTION_UTLATANDE_BASERAT_PA_FIELD_ID)
                .name("Utlåtandet är baserat på")
                .dates(checkboxDates)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_UTLATANDE_BASERAT_PA_ID,
                    checkboxDates.stream().map(CheckboxDate::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDateList.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .build()
            )
        )
        .children(List.of(children))
        .pdfConfiguration(
            PdfConfigurationDateList.builder()
                .dateCheckboxes(
                    Map.of(
                        new FieldId(
                            UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(
                                PDF_STATEMENT_BASED_ON_INVESTIGATION_CHECKBOX_FIELD_ID)
                            .dateFieldId(PDF_STATEMENT_BASED_ON_INVESTIGATION_DATE_FIELD_ID)
                            .build(),
                        new FieldId(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(
                                PDF_STATEMENT_BASED_ON_JOURNAL_CHECKBOX_FIELD_ID)
                            .dateFieldId(PDF_STATEMENT_BASED_ON_JOURNAL_DATE_FIELD_ID)
                            .build(),
                        UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID,
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(PDF_STATEMENT_BASED_ON_OTHER_CHECKBOX_FIELD_ID)
                            .dateFieldId(PDF_STATEMENT_BASED_ON_OTHER_DATE_FIELD_ID)
                            .build()
                    )
                )
                .build()
        )
        .includeWhenRenewing(false)
        .build();
  }
}