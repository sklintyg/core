package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfDateValueGeneratorTest {

  private static final String FIELD_ID = "form1[0].#subform[0].flt_dat[0]";
  private static final LocalDate VALUE = LocalDate.now();

  private static final PdfDateValueGenerator pdfDateValueGenerator = new PdfDateValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueDate.class, pdfDateValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithDateValue() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(VALUE.toString())
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDate.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueDate.builder()
        .date(VALUE)
        .build();

    final var result = pdfDateValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfNoDate() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationDate.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueDate.builder()
        .build();

    assertEquals(Collections.emptyList(),
        pdfDateValueGenerator.generate(elementSpecification, elementValue)
    );
  }
}
