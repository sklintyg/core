package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;

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
                    .id(new FieldId("foraldersBeskrivning"))
                    .label("förälders beskrivning av barnet")
                    .code(
                        new Code(
                            "FORALDER",
                            "KV_FKMU_0001",
                            "förälders beskrivning av barnet"
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
                    "$fysisktMote || $digitaltMote || $journaluppgifter || $foraldersBeskrivning || $annat"
                )
            )
            .build()
    );

    final var element = questionGrundForMedicinsktUnderlag();

    assertIterableEquals(expectedRules, element.rules());
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

    assertIterableEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expectedPdfConfiguration = PdfConfigurationDateList.builder()
        .dateCheckboxes(
            Map.of(
                new FieldId("fysisktMote"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(
                        new PdfFieldId("form1[0].#subform[0].ksr_UndersokningFysiskt[0]"))
                    .dateFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumUndersokningFysiskt[0]"))
                    .build(),
                new FieldId("digitaltMote"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(
                        new PdfFieldId("form1[0].#subform[0].ksr_UndersokningDigitalt[0]"))
                    .dateFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumUndersokningDigitalt[0]"))
                    .build(),
                new FieldId("journaluppgifter"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(new PdfFieldId("form1[0].#subform[0].ksr_Journaluppgifter[0]"))
                    .dateFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumJournaluppgifter[0]"))
                    .build(),
                new FieldId("foraldersBeskrivning"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(
                        new PdfFieldId("form1[0].#subform[0].ksr_ForaldersBeskrivning[0]"))
                    .dateFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumAnhorig[0]")
                    )
                    .build(),
                new FieldId("annat"),
                PdfConfigurationDateCheckbox.builder()
                    .checkboxFieldId(new PdfFieldId("form1[0].#subform[0].ksr_AnnatFyll[0]"))
                    .dateFieldId(new PdfFieldId("form1[0].#subform[0].flt_datumAnnat[0]"))
                    .build()
            )
        )
        .build();

    final var element = QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag();

    assertEquals(expectedPdfConfiguration, element.pdfConfiguration());
  }

  @Test
  void shouldSetIncludeWhenRenewing() {
    final var element = QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag();
    assertFalse(element.includeWhenRenewing());
  }
}
