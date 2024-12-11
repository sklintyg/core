package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class PrintCertificateQuestionConverterTest {

  private static final LocalDate DATE = LocalDate.now();

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .id(new ElementId("1"))
      .configuration(
          ElementConfigurationDate.builder()
              .name("Beräknat födelsedatum")
              .build()
      )
      .build();

  private static final ElementData ELEMENT_DATA =
      ElementData.builder()
          .id(new ElementId("1"))
          .value(
              ElementValueDate.builder()
                  .date(DATE)
                  .build()
          ).build();

  private static final ElementData ELEMENT_DATA_EMPTY = ElementData.builder()
      .id(new ElementId("1"))
      .value(
          ElementValueDate.builder()
              .dateId(new FieldId("1.1"))
              .build()
      )
      .build();

  private final PrintCertificateQuestionConverter printCertificateQuestionConverter = new PrintCertificateQuestionConverter();

  @Test
  void shouldSetName() {
    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, Optional.of(ELEMENT_DATA)
    );

    assertEquals("Beräknat födelsedatum", response.get().getName());
  }

  @Test
  void shouldReturnNullIfNoElementDate() {
    final var response = printCertificateQuestionConverter.convert(
        ElementSpecification.builder().id(new ElementId("2")).build(), Optional.of(ELEMENT_DATA)
    );

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldSetValue() {
    final var expected = ElementSimplifiedValueText.builder()
        .text(DATE.format(DateTimeFormatter.ISO_DATE))
        .build();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, Optional.of(ELEMENT_DATA)
    );

    assertEquals(expected, response.get().getValue());
  }

  @Test
  void shouldReturnOptionalEmptyIfValueIsEmpty() {
    final var expected = Optional.empty();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, Optional.of(ELEMENT_DATA_EMPTY)
    );

    assertEquals(expected, response);
  }
}