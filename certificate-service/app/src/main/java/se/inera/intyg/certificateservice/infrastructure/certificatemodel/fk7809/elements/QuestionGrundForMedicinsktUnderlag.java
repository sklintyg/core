package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

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

public class QuestionGrundForMedicinsktUnderlag {

  public static final ElementId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID = new ElementId("1");
  public static final FieldId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID = new FieldId("1.1");

  public static final String UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID = "undersokningAvPatienten";
  public static final String UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID = "journaluppgifter";
  public static final FieldId UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID = new FieldId("anhorig");
  public static final FieldId UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("annat");

  public static final PdfFieldId PDF_EXAMINATION_CHECKBOX_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_UndersokningPatienten[0]");
  public static final PdfFieldId PDF_EXAMINATION_DATE_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datumUndersokningPatient[0]");
  public static final PdfFieldId PDF_HEALTH_RECORDS_CHECKBOX_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_Journaluppgifter[0]");
  public static final PdfFieldId PDF_HEALTH_RECORDS_DATE_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datumJournaluppgifter[0]");
  public static final PdfFieldId PDF_FAMILY_STATEMENT_CHECKBOX_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_AnhorigAnnan[0]");
  public static final PdfFieldId PDF_FAMILY_STATEMENT_DATE_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datumAnhorig[0]");
  public static final PdfFieldId PDF_OTHER_CHECKBOX_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_AnnatFyll[0]");
  public static final PdfFieldId PDF_OTHER_DATE_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datumAnnat[0]");

  private QuestionGrundForMedicinsktUnderlag() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGrundForMedicinsktUnderlag(
      ElementSpecification... children) {
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
            .label("journaluppgifter från och med")
            .code(CodeSystemKvFkmu0001.JOURNALUPPGIFTER)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID)
            .label(CodeSystemKvFkmu0001.ANHORIG_V1.displayName())
            .code(CodeSystemKvFkmu0001.ANHORIG_V1)
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
        .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID)
                .name("Utlåtandet är baserat på")
                .dates(checkboxDates)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
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
        .pdfConfiguration(
            PdfConfigurationDateList.builder()
                .dateCheckboxes(
                    Map.of(
                        new FieldId(
                            UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(
                                PDF_EXAMINATION_CHECKBOX_ID)
                            .dateFieldId(PDF_EXAMINATION_DATE_ID)
                            .build(),
                        new FieldId(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(
                                PDF_HEALTH_RECORDS_CHECKBOX_ID)
                            .dateFieldId(PDF_HEALTH_RECORDS_DATE_ID)
                            .build(),
                        UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID,
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(PDF_FAMILY_STATEMENT_CHECKBOX_ID)
                            .dateFieldId(PDF_FAMILY_STATEMENT_DATE_ID)
                            .build(),
                        UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID,
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(PDF_OTHER_CHECKBOX_ID)
                            .dateFieldId(PDF_OTHER_DATE_ID)
                            .build()
                    )
                )
                .build()
        )
        .includeWhenRenewing(false)
        .children(List.of(children))
        .build();
  }
}