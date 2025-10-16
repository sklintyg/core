package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;

public class QuestionGrundForMedicinsktUnderlag {

  public static final ElementId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID = new ElementId("1");
  public static final FieldId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID = new FieldId("1.1");

  private QuestionGrundForMedicinsktUnderlag() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGrundForMedicinsktUnderlag(
      ElementSpecification... children) {
    final var checkboxDates = List.of(
        CheckboxDate.builder()
            .id(new FieldId(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING.code()))
            .label(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING.displayName())
            .code(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING.code()))
            .label(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING.displayName())
            .code(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId(CodeSystemKvFkmu0001.TELEFONKONTAKT.code()))
            .label(CodeSystemKvFkmu0001.TELEFONKONTAKT.displayName())
            .code(CodeSystemKvFkmu0001.TELEFONKONTAKT)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId(CodeSystemKvFkmu0001.JOURNALUPPGIFTER.code()))
            .label(CodeSystemKvFkmu0001.JOURNALUPPGIFTER.displayName())
            .code(CodeSystemKvFkmu0001.JOURNALUPPGIFTER)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId(CodeSystemKvFkmu0001.ANNAT.code()))
            .label(CodeSystemKvFkmu0001.ANNAT.displayName())
            .code(CodeSystemKvFkmu0001.ANNAT)
            .max(Period.ofDays(0))
            .build()
    );

    return ElementSpecification.builder()
        .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID)
                .name("Intyget är baserat på")
                .dates(checkboxDates)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    checkboxDates.stream().map(CheckboxDate::id).toList()
                ),
                CertificateElementRuleFactory.hide(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
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
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false)
        )
        .children(List.of(children))
        .pdfConfiguration(
            PdfConfigurationDateList.builder()
                .dateCheckboxes(
                    Map.of(
                        new FieldId(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING.code()),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(
                                new PdfFieldId("form1[0].#subform[0].ksr_FysisktVardmote[0]"))
                            .dateFieldId(new PdfFieldId("form1[0].#subform[0].flt_datumPatient[0]"))
                            .build(),
                        new FieldId(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING.code()),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(
                                new PdfFieldId("form1[0].#subform[0].ksr_DigitalVardmote[0]"))
                            .dateFieldId(
                                new PdfFieldId("form1[0].#subform[0].flt_datumDigitaltVardmöte[0]"))
                            .build(),
                        new FieldId(CodeSystemKvFkmu0001.TELEFONKONTAKT.code()),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(new PdfFieldId(
                                "form1[0].#subform[0].ksr_TelefonkontaktPatienten[0]"))
                            .dateFieldId(
                                new PdfFieldId("form1[0].#subform[0].flt_datumTelefonkontakt[0]"))
                            .build(),
                        new FieldId(CodeSystemKvFkmu0001.JOURNALUPPGIFTER.code()),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(
                                new PdfFieldId("form1[0].#subform[0].ksr_Journaluppgifter[0]"))
                            .dateFieldId(
                                new PdfFieldId("form1[0].#subform[0].flt_datumJournaluppgifter[0]"))
                            .build(),
                        new FieldId(CodeSystemKvFkmu0001.ANNAT.code()),
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(new PdfFieldId("form1[0].#subform[0].ksr_Annat[0]"))
                            .dateFieldId(new PdfFieldId("form1[0].#subform[0].flt_datumAnnat[0]"))
                            .build()
                    )
                )
                .build()
        )
        .includeWhenRenewing(false)
        .build();
  }
}
