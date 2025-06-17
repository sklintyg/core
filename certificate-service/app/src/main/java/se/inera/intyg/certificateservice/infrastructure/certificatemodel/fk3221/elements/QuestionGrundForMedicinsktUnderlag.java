package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;

public class QuestionGrundForMedicinsktUnderlag {

  public static final ElementId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID = new ElementId("1");
  public static final FieldId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID = new FieldId("1.1");

  public static final FieldId UTLATANDE_BASERAT_PA_FYSISKT_MOTE_FIELD_ID = new FieldId(
      "fysisktMote");
  public static final FieldId UTLATANDE_BASERAT_PA_DIGITALT_VARDMOTE_FIELD_ID = new FieldId(
      "digitaltMote");
  public static final FieldId UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID = new FieldId(
      "journaluppgifter");
  public static final FieldId UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID = new FieldId("anhorig");
  public static final FieldId UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("annat");

  private QuestionGrundForMedicinsktUnderlag() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGrundForMedicinsktUnderlag(
      ElementSpecification... children) {
    final var checkboxDates = List.of(
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_FYSISKT_MOTE_FIELD_ID)
            .label(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING.displayName())
            .code(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_DIGITALT_VARDMOTE_FIELD_ID)
            .label(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING.displayName())
            .code(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID)
            .label(CodeSystemKvFkmu0001.JOURNALUPGIFTER.displayName())
            .code(CodeSystemKvFkmu0001.JOURNALUPGIFTER)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID)
            .label(CodeSystemKvFkmu0001.ANHORIG.displayName())
            .code(CodeSystemKvFkmu0001.ANHORIG)
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
        .children(List.of(children))
        .build();
  }
}