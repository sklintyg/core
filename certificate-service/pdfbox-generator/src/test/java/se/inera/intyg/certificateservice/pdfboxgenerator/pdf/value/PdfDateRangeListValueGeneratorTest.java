package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeCheckbox;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDateRangeListValueGeneratorTest {

  private static final String FIELD_PREFIX = "form1[0].#subform[0]";

  private static final DateRange DATE_RANGE = DateRange.builder()
      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.HELA.code()))
      .from(LocalDate.now().minusDays(1))
      .to(LocalDate.now().plusDays(10))
      .build();

  private static final DateRange DATE_RANGE_2 = DateRange.builder()
      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.EN_FJARDEDEL.code()))
      .from(LocalDate.now().minusDays(5))
      .to(LocalDate.now().plusDays(7))
      .build();

  private static final DateRange DATE_RANGE_3 = DateRange.builder()
      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.HALVA.code()))
      .from(LocalDate.now().minusDays(5))
      .to(LocalDate.now().plusDays(7))
      .build();

  private static final DateRange DATE_RANGE_ONLY_FROM = DateRange.builder()
      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.EN_ATTONDEL.code()))
      .from(LocalDate.now().minusDays(1))
      .build();

  private static final DateRange DATE_RANGE_ONLY_TO = DateRange.builder()
      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.TRE_FJARDEDELAR.code()))
      .to(LocalDate.now().plusDays(10))
      .build();

  private static final PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator = new PdfDateRangeListValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueDateRangeList.class, pdfDateRangeListValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithDateRangeList() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateRangeList.builder()
                .dateRanges(
                    Map.of(
                        DATE_RANGE.dateRangeId(),
                        PdfConfigurationDateRangeCheckbox.builder()
                            .checkbox(new PdfFieldId(FIELD_PREFIX + ".ksr_Hela[0]"))
                            .from(new PdfFieldId(FIELD_PREFIX + ".flt_datFranMed[0]"))
                            .to(new PdfFieldId(FIELD_PREFIX + ".flt_datLangstTillMed[0]"))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                DATE_RANGE
            )
        )
        .build();

    final var result = pdfDateRangeListValueGenerator.generate(elementSpecification, elementValue);

    assertAll(
        () -> assertEquals(3, result.size()),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".ksr_Hela[0]")
                .value("1")
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datFranMed[0]")
                .value(DATE_RANGE.from().toString())
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datLangstTillMed[0]")
                .value(DATE_RANGE.to().toString())
                .build())
        )
    );
  }

  @Test
  void shouldSetValueIfElementDataWithDateRangeListIncludingSeveralDateRanges() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateRangeList.builder()
                .dateRanges(
                    Map.of(
                        DATE_RANGE.dateRangeId(),
                        PdfConfigurationDateRangeCheckbox.builder()
                            .checkbox(new PdfFieldId(FIELD_PREFIX + ".ksr_Hela[0]"))
                            .from(new PdfFieldId(FIELD_PREFIX + ".flt_datFranMed[0]"))
                            .to(new PdfFieldId(FIELD_PREFIX + ".flt_datLangstTillMed[0]"))
                            .build(),
                        DATE_RANGE_2.dateRangeId(),
                        PdfConfigurationDateRangeCheckbox.builder()
                            .checkbox(new PdfFieldId(FIELD_PREFIX + ".ksr_Enfjardedela[0]"))
                            .from(new PdfFieldId(FIELD_PREFIX + ".flt_datFranMed4[0]"))
                            .to(new PdfFieldId(FIELD_PREFIX + ".flt_datLangstTillMed4[0]"))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                DATE_RANGE,
                DATE_RANGE_2
            )
        )
        .build();

    final var result = pdfDateRangeListValueGenerator.generate(elementSpecification, elementValue);

    assertAll(
        () -> assertEquals(6, result.size()),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".ksr_Hela[0]")
                .value("1")
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datFranMed[0]")
                .value(DATE_RANGE.from().toString())
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datLangstTillMed[0]")
                .value(DATE_RANGE.to().toString())
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".ksr_Enfjardedela[0]")
                .value("1")
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datFranMed4[0]")
                .value(DATE_RANGE_2.from().toString())
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datLangstTillMed4[0]")
                .value(DATE_RANGE_2.to().toString())
                .build())
        )
    );
  }

  @Test
  void shouldSetValueIfElementDataWithDateRangeListIncludingSeveralDateRangesAndSomeNotWhole() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDateRangeList.builder()
                .dateRanges(
                    Map.of(
                        DATE_RANGE_ONLY_FROM.dateRangeId(),
                        PdfConfigurationDateRangeCheckbox.builder()
                            .checkbox(new PdfFieldId(FIELD_PREFIX + ".ksr_EnAttondel[0]"))
                            .from(new PdfFieldId(FIELD_PREFIX + ".flt_datFranMed5[0]"))
                            .build(),
                        DATE_RANGE_3.dateRangeId(),
                        PdfConfigurationDateRangeCheckbox.builder()
                            .checkbox(new PdfFieldId(FIELD_PREFIX + ".ksr_Halva[0]"))
                            .from(new PdfFieldId(FIELD_PREFIX + ".flt_datFranMed3[0]"))
                            .to(new PdfFieldId(FIELD_PREFIX + ".flt_datLangstTillMed3[0]"))
                            .build(),
                        DATE_RANGE_ONLY_TO.dateRangeId(),
                        PdfConfigurationDateRangeCheckbox.builder()
                            .checkbox(new PdfFieldId(FIELD_PREFIX + ".ksr_Trefjardedela[0]"))
                            .to(new PdfFieldId(FIELD_PREFIX + ".flt_datLangstTillMed2[0]"))
                            .build()
                    )
                )
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                DATE_RANGE_ONLY_FROM,
                DATE_RANGE_3,
                DATE_RANGE_ONLY_TO
            )
        )
        .build();

    final var result = pdfDateRangeListValueGenerator.generate(elementSpecification, elementValue);

    assertAll(
        () -> assertEquals(7, result.size()),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".ksr_Halva[0]")
                .value("1")
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datFranMed3[0]")
                .value(DATE_RANGE_3.from().toString())
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datLangstTillMed3[0]")
                .value(DATE_RANGE_3.to().toString())
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".ksr_EnAttondel[0]")
                .value("1")
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datFranMed5[0]")
                .value(DATE_RANGE_ONLY_FROM.from().toString())
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".ksr_Trefjardedela[0]")
                .value("1")
                .build())
        ),
        () -> assertTrue(
            result.contains(PdfField.builder()
                .id(FIELD_PREFIX + ".flt_datLangstTillMed2[0]")
                .value(DATE_RANGE_ONLY_TO.to().toString())
                .build())
        )
    );
  }
}
