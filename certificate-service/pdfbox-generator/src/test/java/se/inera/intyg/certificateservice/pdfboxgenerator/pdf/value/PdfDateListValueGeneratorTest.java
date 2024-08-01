package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateCheckbox;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDateListValueGeneratorTest {

  private static final String DATE_FIELD_ID = "form1[0].#subform[0].flt_datUl_1[0]";
  private static final String CHECKBOX_FIELD_ID = "form1[0].#subform[0].ksr_UndersokningPatient[0]";
  private static final FieldId FIELD_ID = new FieldId("undersokningAvPatienten");
  private static final String CHECKBOX_VALUE = "1";
  private static final LocalDate DATE_VALUE = LocalDate.now();

  private static final PdfDateListValueGenerator pdfDateListValueGenerator = new PdfDateListValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueDateList.class, pdfDateListValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithDateListValue() {
    final var expected = List.of(
        PdfField.builder()
            .id(CHECKBOX_FIELD_ID)
            .value(CHECKBOX_VALUE)
            .build(),
        PdfField.builder()
            .id(DATE_FIELD_ID)
            .value(DATE_VALUE.toString())
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateList.builder()
                .dateCheckboxes(
                    Map.of(
                        FIELD_ID,
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(new PdfFieldId(CHECKBOX_FIELD_ID))
                            .dateFieldId(new PdfFieldId(DATE_FIELD_ID))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDateList.builder()
        .dateList(
            List.of(
                ElementValueDate.builder()
                    .dateId(FIELD_ID)
                    .date(DATE_VALUE)
                    .build()
            )
        )
        .dateListId(FIELD_ID)
        .build();

    final var result = pdfDateListValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfNoDateIsProvided() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateList.builder()
                .dateCheckboxes(
                    Map.of(
                        FIELD_ID,
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(new PdfFieldId(CHECKBOX_FIELD_ID))
                            .dateFieldId(new PdfFieldId(DATE_FIELD_ID))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDateList.builder()
        .dateList(
            List.of(
                ElementValueDate.builder()
                    .dateId(FIELD_ID)
                    .build()
            )
        )
        .dateListId(FIELD_ID)
        .build();

    final var result = pdfDateListValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void shouldThrowExceptionIfDateIdIsMissingFromPdfConfiguration() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateList.builder()
                .dateCheckboxes(
                    Map.of(
                        FIELD_ID,
                        PdfConfigurationDateCheckbox.builder()
                            .checkboxFieldId(new PdfFieldId(CHECKBOX_FIELD_ID))
                            .dateFieldId(new PdfFieldId(DATE_FIELD_ID))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDateList.builder()
        .dateList(
            List.of(
                ElementValueDate.builder()
                    .dateId(new FieldId("Missing dateId"))
                    .date(DATE_VALUE)
                    .build()
            )
        )
        .dateListId(FIELD_ID)
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> pdfDateListValueGenerator.generate(elementSpecification, elementValue)
    );
  }
}
