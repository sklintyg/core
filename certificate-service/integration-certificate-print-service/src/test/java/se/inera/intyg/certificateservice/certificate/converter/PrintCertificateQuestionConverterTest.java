package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueTextDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class PrintCertificateQuestionConverterTest {

  private static final LocalDate DATE = LocalDate.now();
  private static final String TEXT = "text";

  private static final String DESCRIPTION = "Beskrivning";
  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .id(new ElementId("1"))
      .configuration(
          ElementConfigurationDate.builder()
              .name("Beräknat födelsedatum")
              .build()
      )
      .children(List.of(
          ElementSpecification.builder()
              .id(new ElementId("2"))
              .configuration(ElementConfigurationTextArea.builder()
                  .name(DESCRIPTION)
                  .build())
              .build()
      ))
      .build();

  private static final Certificate CERTIFICATE =
      MedicalCertificate.builder()
          .elementData(List.of(ElementData.builder()
                  .id(new ElementId("1"))
                  .value(
                      ElementValueDate.builder()
                          .date(DATE)
                          .build()
                  ).build(),
              ElementData.builder()
                  .id(new ElementId("2"))
                  .value(
                      ElementValueText.builder()
                          .text(TEXT)
                          .build()
                  ).build())
          )
          .build();

  private static final Certificate CERTIFICATE_EMPTY = MedicalCertificate.builder()
      .elementData(List.of(ElementData.builder()
          .id(new ElementId("1"))
          .value(
              ElementValueDate.builder()
                  .dateId(new FieldId("1.1"))
                  .build()
          )
          .build()))
      .build();

  private final PrintCertificateQuestionConverter printCertificateQuestionConverter = new PrintCertificateQuestionConverter();

  @Test
  void shouldSetName() {
    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE
    );

    assertEquals("Beräknat födelsedatum", response.get().getName());
  }

  @Test
  void shouldSetId() {
    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE
    );

    assertEquals("1", response.get().getId());
  }


  @Test
  void shouldReturnNullIfNoElementDate() {
    final var response = printCertificateQuestionConverter.convert(
        ElementSpecification.builder().id(new ElementId("2")).build(), CERTIFICATE_EMPTY
    );

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldSetValue() {
    final var expected = ElementSimplifiedValueTextDTO.builder()
        .text(DATE.format(DateTimeFormatter.ISO_DATE))
        .build();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE
    );

    assertEquals(expected, response.get().getValue());
  }

  @Test
  void shouldReturnOptionalEmptyIfValueIsEmpty() {
    final var expected = Optional.empty();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE_EMPTY
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldConvertChildren() {
    final var expected = PrintCertificateQuestionDTO.builder()
        .name(DESCRIPTION)
        .id("2")
        .value(ElementSimplifiedValueTextDTO.builder()
            .text(TEXT)
            .build())
        .subquestions(Collections.emptyList())
        .build();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE
    );

    assertEquals(expected, response.get().getSubquestions().getFirst());
  }
}