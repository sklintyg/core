package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;

import java.time.Period;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateCheckbox;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;

class QuestionGrundForMedicinsktUnderlagTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");

  @Test
  void shallIncludeId() {
    final var element = questionGrundForMedicinsktUnderlag();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleDate.builder()
        .name("Utlåtandet är baserat på")
        .id(new FieldId("1.1"))
        .dates(
            List.of(
                CheckboxDate.builder()
                    .id(new FieldId("fysisktMote"))
                    .label("min undersökning vid fysiskt vårdmöte")
                    .code(
                        new Code(
                            "FYSISKUNDERSOKNING",
                            "KV_FKMU_0001",
                            "min undersökning vid fysiskt vårdmöte"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("digitaltMote"))
                    .label("min undersökning vid digitalt vårdmöte")
                    .code(
                        new Code(
                            "DIGITALUNDERSOKNING",
                            "KV_FKMU_0001",
                            "min undersökning vid digitalt vårdmöte"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("journaluppgifter"))
                    .label("journaluppgifter från den")
                    .code(
                        new Code(
                            "JOURNALUPPGIFTER",
                            "KV_FKMU_0001",
                            "journaluppgifter från den"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("anhorig"))
                    .label("anhörigs eller annans beskrivning av patienten")
                    .code(
                        new Code(
                            "ANHORIG",
                            "KV_FKMU_0001",
                            "anhörigs eller annans beskrivning av patienten"
                        )
                    )
                    .max(Period.ZERO)
                    .build(),
                CheckboxDate.builder()
                    .id(new FieldId("annat"))
                    .label("annat")
                    .code(
                        new Code(
                            "ANNAT",
                            "KV_FKMU_0001",
                            "annat"
                        )
                    )
                    .max(Period.ZERO)
                    .build()
            )
        )
        .build();

    final var element = questionGrundForMedicinsktUnderlag();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "$fysisktMote || $digitaltMote || $journaluppgifter || $anhorig || $annat"
                )
            )
            .build()
    );

    final var element = questionGrundForMedicinsktUnderlag();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDateList.builder()
            .mandatory(true)
            .max(Period.ofDays(0))
            .build()
    );

    final var element = questionGrundForMedicinsktUnderlag();

    assertEquals(expectedValidations, element.validations());
  }


  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationDateList.builder()
        .dateCheckboxes(
            Map.of(
                new FieldId("fysisktMote"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(
                        new PdfFieldId("form1[0].#subform[0].ksr_UndersokningPatienten[0]")
                    )
                    .dateFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumUndersokningPatient[0]"))
                    .build(),
                new FieldId("digitaltMote"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(
                        new PdfFieldId("form1[0].#subform[0].ksr_Kontaktsatt[0]")
                    )
                    .dateFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_txtdatumDigitaltVardmote[0]"))
                    .build(),
                new FieldId("journaluppgifter"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(
                        new PdfFieldId("form1[0].#subform[0].ksr_Journaluppgifter[0]")
                    )
                    .dateFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumJournaluppgfiter[0]"))
                    .build(),
                new FieldId("anhorig"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(new PdfFieldId("form1[0].#subform[0].ksr_Anhorig[0]"))
                    .dateFieldId(new PdfFieldId("form1[0].#subform[0].flt_datumAnhorig[0]"))
                    .build(),
                new FieldId("annat"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(new PdfFieldId("form1[0].#subform[0].ksr_Annat[0]"))
                    .dateFieldId(new PdfFieldId("form1[0].#subform[0].flt_datumAnnat[0]"))
                    .build()
            )
        )
        .build();

    final var element = questionGrundForMedicinsktUnderlag();

    assertEquals(expected, element.pdfConfiguration());
  }

  @Test
  void shouldSetIncludeWhenRenewing() {
    final var element = questionGrundForMedicinsktUnderlag();
    assertFalse(element.includeWhenRenewing());
  }
}
