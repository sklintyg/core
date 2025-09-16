package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

class PdfintegerValueGeneratorTest {

  private static final String FIELD_ID = "form1[0].#subform[0].flt_txtInteger[0]";
  private static final Integer VALUE = 42;

  private static final PdfintegerValueGenerator pdfintegerValueGenerator = new PdfintegerValueGenerator();

  @Test
  void shouldReturnType() {
    assertEquals(ElementValueInteger.class, pdfintegerValueGenerator.getType());
  }

  @Test
  void shouldSetValueIfElementDataWithIntegerValue() {
    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(VALUE.toString())
            .offset(10)
            .build()
    );

    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .offset(10)
                .build()
        )
        .build();

    final var elementValue = ElementValueInteger.builder()
        .value(VALUE)
        .build();

    final var result = pdfintegerValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnEmptyListIfElementDataWithoutIntegerValue() {
    final var elementSpecification = ElementSpecification.builder()
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(FIELD_ID))
                .build()
        )
        .build();

    final var elementValue = ElementValueInteger.builder()
        .build();

    final var result = pdfintegerValueGenerator.generate(elementSpecification, elementValue);

    assertEquals(Collections.emptyList(), result);
  }
}

