package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueLabeledListDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueLabeledTextDTO;
import se.inera.intyg.certificateservice.certificate.dto.ElementSimplifiedValueTextDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenPdfConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class PrintCertificateQuestionConverterTest {

  private static final LocalDate DATE = LocalDate.now();
  private static final String TEXT = "text";

  private static final String DESCRIPTION = "Beskrivning";
  private static final List<CheckboxDate> checkboxDates = List.of(
      CheckboxDate.builder()
          .id(new FieldId("FieldId 1"))
          .label("Label 1")
          .code(new Code("Code 1", "Display Name 1", "System 1"))
          .max(Period.ofDays(0))
          .build(),
      CheckboxDate.builder()
          .id(new FieldId("FieldId 2"))
          .label("Label 2")
          .code(new Code("Code 2", "Display Name 2", "System 2"))
          .max(Period.ofDays(0))
          .build()
  );

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
              .configuration(
                  ElementConfigurationTextArea.builder()
                      .name(DESCRIPTION)
                      .build())
              .build()
      ))
      .build();

  private static final ElementSpecification ELEMENT_SPECIFICATION_MULTIPLE_DATES = ElementSpecification.builder()
      .id(new ElementId("2"))
      .configuration(
          ElementConfigurationCheckboxMultipleDate.builder()
              .name("Datum")
              .dates(checkboxDates)
              .build()
      )
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

  private static final Certificate CERTIFICATE_MULTIPLE_DATES =
      MedicalCertificate.builder()
          .elementData(List.of(
              ElementData.builder()
                  .id(new ElementId("2"))
                  .value(
                      ElementValueDateList.builder()
                          .dateList(List.of(ElementValueDate.builder()
                              .dateId(new FieldId("FieldId 1"))
                              .date(DATE)
                              .build()))
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

  private static final ElementSpecification ELEMENT_SPECIFICATION_HIDDEN_VALUES = ElementSpecification.builder()
      .id(new ElementId("1"))
      .configuration(ElementConfigurationTextArea.builder()
          .name(DESCRIPTION)
          .build())
      .pdfConfiguration(
          CitizenPdfConfiguration.builder()
              .hiddenBy(new ElementId("1"))
              .replacementValue(ElementSimplifiedValueText.builder()
                  .text("Dold information")
                  .build())
              .build()
      )
      .build();

  private static final List<ElementId> HIDDEN_ELEMENTS = List.of(new ElementId("1"));
  private static final boolean IS_CITIZEN_FORMAT = false;
  private static final PdfGeneratorOptions OPTIONS = PdfGeneratorOptions.builder()
      .additionalInfoText(TEXT)
      .citizenFormat(IS_CITIZEN_FORMAT)
      .hiddenElements(HIDDEN_ELEMENTS)
      .build();
  private static final PdfGeneratorOptions OPTIONS_CITIZEN = PdfGeneratorOptions.builder()
      .additionalInfoText(TEXT)
      .citizenFormat(true)
      .hiddenElements(HIDDEN_ELEMENTS)
      .build();

  private final PrintCertificateQuestionConverter printCertificateQuestionConverter = new PrintCertificateQuestionConverter();

  @Test
  void shouldSetName() {
    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE, OPTIONS
    );

    assertEquals("Beräknat födelsedatum", response.get().getName());
  }

  @Test
  void shouldSetId() {
    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE, OPTIONS
    );

    assertEquals("1", response.get().getId());
  }

  @Test
  void shouldSetValue() {
    final var expected = ElementSimplifiedValueTextDTO.builder()
        .text(DATE.format(DateTimeFormatter.ISO_DATE))
        .build();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE, OPTIONS
    );

    assertEquals(expected, response.get().getValue());
  }

  @Test
  void shouldReturnSimplifiedValueIfEmpty() {
    final var expected = Optional.of(
        PrintCertificateQuestionDTO.builder()
            .id("1")
            .name("Beräknat födelsedatum")
            .value(ElementSimplifiedValueTextDTO.builder()
                .text("Ej angivet")
                .build())
            .subquestions(
                List.of(
                    PrintCertificateQuestionDTO.builder()
                        .id("2")
                        .name("Beskrivning")
                        .value(
                            ElementSimplifiedValueTextDTO.builder()
                                .text("Ej angivet")
                                .build()
                        )
                        .subquestions(Collections.emptyList())
                        .build()
                )
            )
            .build()
    );

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION, CERTIFICATE_EMPTY, OPTIONS
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
        ELEMENT_SPECIFICATION, CERTIFICATE, OPTIONS
    );

    assertEquals(expected, response.get().getSubquestions().getFirst());
  }

  @Test
  void shouldSetValueMultipleDates() {
    final var expected = ElementSimplifiedValueLabeledListDTO.builder()
        .list(List.of(ElementSimplifiedValueLabeledTextDTO.builder()
                .label("Label 1")
                .text(DATE.toString())
                .build(),
            ElementSimplifiedValueLabeledTextDTO.builder().label("Label 2").text("Ej angivet")
                .build()))
        .build();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION_MULTIPLE_DATES, CERTIFICATE_MULTIPLE_DATES, OPTIONS_CITIZEN
    );

    assertEquals(expected, response.get().getValue());
  }

  @Test
  void shouldReplaceSimplifiedValue() {
    final var expected = ElementSimplifiedValueTextDTO.builder()
        .text("Dold information")
        .build();

    final var response = printCertificateQuestionConverter.convert(
        ELEMENT_SPECIFICATION_HIDDEN_VALUES, CERTIFICATE, OPTIONS_CITIZEN
    );

    assertEquals(expected, response.get().getValue());
  }

  @Test
  void shouldReturnEmptyWhenElementIsHidden() {
    final var response = printCertificateQuestionConverter.convert(
        ElementSpecification.builder()
            .id(new ElementId("1"))
            .shouldValidate(List::isEmpty)
            .build(), CERTIFICATE, OPTIONS
    );

    assertTrue(response.isEmpty());
  }
}