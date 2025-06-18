package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.xml.bind.JAXBContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateRange;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class PrefillDateRangeConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId FIELD_ID = new FieldId("F2");
  private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
  private static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationDateRange.builder()
              .id(FIELD_ID)
              .id(FIELD_ID)
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDateRange.builder()
              .id(FIELD_ID)
              .fromDate(START_DATE)
              .toDate(END_DATE)
              .build()
      ).build();

  private final PrefillDateRangeConverter prefillDateRangeConverter = new PrefillDateRangeConverter();
  private final XmlGeneratorDateRange xmlGeneratorRadioMultipleDateRange = new XmlGeneratorDateRange();

  @Test
  void shouldReturnSupportsDateRange() {
    assertEquals(ElementConfigurationDateRange.class,
        prefillDateRangeConverter.supports());
  }

  @Nested
  class Answer {

    @Test
    void shouldReturnEmptyListForUnknownIds() {
      assertEquals(
          Collections.emptyList(),
          prefillDateRangeConverter.unknownIds(null, null)
      );
    }

    @Test
    void shouldCreatPrefillAnswerForDateRange() throws Exception {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var answer = new Svar();
      final var subAnswer = new Delsvar();
      subAnswer.getContent().add(createDateRangeTypeElement());
      answer.getDelsvar().add(subAnswer);

      final var result = prefillDateRangeConverter.prefillAnswer(List.of(answer),
          SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillDateRangeConverter.prefillAnswer(
          List.of(new Svar()),
          wrongConfiguration
      );

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneAnswer() {
      final var answer = xmlGeneratorRadioMultipleDateRange.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioMultipleDateRange.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillDateRangeConverter.prefillAnswer(
          List.of(answer.getFirst(), answer2.getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoAnswers() {
      final var result = prefillDateRangeConverter.prefillAnswer(List.of(), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidDateRangeFormat() {
      final var answer = new Svar();
      final var subAnswer = new Delsvar();
      final var content = List.of("invalid-dateRange-format");
      subAnswer.getContent().add(content);
      answer.getDelsvar().add(subAnswer);

      final var result = prefillDateRangeConverter.prefillAnswer(List.of(answer),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }
  }

  @Nested
  class SubAnswer {

    @Test
    void shouldReturnPrefillAnswerForSubAnswer() throws Exception {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var subAnswer = new Delsvar();
      subAnswer.getContent().add(createDateRangeTypeElement());

      final var result = prefillDateRangeConverter.prefillSubAnswer(
          List.of(subAnswer),
          SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationTypeForSubAnswer() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillDateRangeConverter.prefillSubAnswer(
          List.of(new Delsvar()),
          wrongConfiguration);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneSubAnswer() {
      final var answer = xmlGeneratorRadioMultipleDateRange.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioMultipleDateRange.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillDateRangeConverter.prefillSubAnswer(
          List.of(answer.getFirst().getDelsvar().getFirst(),
              answer2.getFirst().getDelsvar().getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoSubAnswers() {
      final var result = prefillDateRangeConverter.prefillSubAnswer(List.of(),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidFormat() {
      final var subAnswer = new Delsvar();
      final var content = List.of("invalid-dateRange-format");
      subAnswer.getContent().add(content);

      final var result = prefillDateRangeConverter.prefillSubAnswer(List.of(subAnswer),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }
  }

  private static Element createDateRangeTypeElement() throws Exception {
    final var dateRange = new DatePeriodType();
    dateRange.setStart(toXmlGregorianCalendar(START_DATE));
    dateRange.setEnd(toXmlGregorianCalendar(END_DATE));

    final var factory = new ObjectFactory();
    final var jaxbElement = factory.createDatePeriod(dateRange);

    final var context = JAXBContext.newInstance(DatePeriodType.class);
    final var marshaller = context.createMarshaller();
    final var writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);

    final var docFactory = DocumentBuilderFactory.newInstance();
    docFactory.setNamespaceAware(true);
    final var doc = docFactory.newDocumentBuilder()
        .parse(new org.xml.sax.InputSource(new StringReader(writer.toString())));
    return doc.getDocumentElement();
  }

  private static XMLGregorianCalendar toXmlGregorianCalendar(LocalDate date) {
    if (date == null) {
      return null;
    }

    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
    } catch (Exception ex) {
      throw new IllegalStateException("Could not convert date to XML Gregorian format", ex);
    }
  }
}