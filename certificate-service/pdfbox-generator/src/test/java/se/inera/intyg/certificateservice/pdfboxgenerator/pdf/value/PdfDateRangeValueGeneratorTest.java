package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDateRangeValueGeneratorTest {

  private static final String FROM_FIELD_ID = "form1[0].#subform[0].from_date[0]";
  private static final String TO_FIELD_ID = "form1[0].#subform[0].to_date[0]";
  private static final LocalDate FROM_DATE = LocalDate.now().minusDays(10);
  private static final LocalDate TO_DATE = LocalDate.now();

  private static final PdfDateRangeValueGenerator pdfDateRangeValueGenerator = new PdfDateRangeValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueDateRange.class, pdfDateRangeValueGenerator.getType());
  }

  @Test
  void shouldSetValuesIfDateRangeProvided() {
    final var expected = List.of(
        PdfField.builder()
            .id(FROM_FIELD_ID)
            .value(FROM_DATE.toString())
            .build(),
        PdfField.builder()
            .id(TO_FIELD_ID)
            .value(TO_DATE.toString())
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateRange.builder()
                .from(new PdfFieldId(FROM_FIELD_ID))
                .to(new PdfFieldId(TO_FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRange.builder()
        .fromDate(FROM_DATE)
        .toDate(TO_DATE)
        .build();

    final var result = pdfDateRangeValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldSetOnlyFromDateIfToDateIsNull() {
    final var expected = List.of(
        PdfField.builder()
            .id(FROM_FIELD_ID)
            .value(FROM_DATE.toString())
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateRange.builder()
                .from(new PdfFieldId(FROM_FIELD_ID))
                .to(new PdfFieldId(TO_FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRange.builder()
        .fromDate(FROM_DATE)
        .build();

    final var result = pdfDateRangeValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldSetOnlyToDateIfFromDateIsNull() {
    final var expected = List.of(
        PdfField.builder()
            .id(TO_FIELD_ID)
            .value(TO_DATE.toString())
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateRange.builder()
                .from(new PdfFieldId(FROM_FIELD_ID))
                .to(new PdfFieldId(TO_FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRange.builder()
        .toDate(TO_DATE)
        .build();

    final var result = pdfDateRangeValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfBothDatesAreNull() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateRange.builder()
                .from(new PdfFieldId(FROM_FIELD_ID))
                .to(new PdfFieldId(TO_FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRange.builder().build();

    final var result = pdfDateRangeValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(List.of(), result);
  }
}
