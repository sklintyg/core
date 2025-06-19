package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;

class PrefillAnswerTest {

  @Test
  void shouldReturnAnswerNotFoundError() {
    final var answer = PrefillAnswer.answerNotFound("MI1");
    assertAll(
        () -> assertEquals(1, answer.getErrors().size()),
        () -> assertEquals(PrefillErrorType.ANSWER_NOT_FOUND, answer.getErrors().getFirst().type()),
        () -> assertEquals("Answer with id MI1 not found in certificate model",
            answer.getErrors().getFirst().details())
    );
  }

  @Test
  void shouldReturnSubAnswerNotFoundError() {
    final var answer = PrefillAnswer.subAnswerNotFound("MI1", "MI1.1");
    assertAll(
        () -> assertEquals(1, answer.getErrors().size()),
        () -> assertEquals(PrefillErrorType.SUB_ANSWER_NOT_FOUND,
            answer.getErrors().getFirst().type()),
        () -> assertEquals("Sub-answer with id MI1.1 not found in answer with id MI1",
            answer.getErrors().getFirst().details())
    );
  }

  @Test
  void shouldReturnInvalidFormatError() {
    final var answer = PrefillAnswer.invalidFormat();
    assertAll(
        () -> assertEquals(1, answer.getErrors().size()),
        () -> assertEquals(PrefillErrorType.INVALID_FORMAT, answer.getErrors().getFirst().type()),
        () -> assertEquals("Invalid format", answer.getErrors().getFirst().details())
    );
  }

  @Test
  void shouldReturnEmptyOptionalWhenUnmarshalTypeWithNoElement() {
    final var result = PrefillAnswer.unmarshalCVType(List.of("not-an-element"));
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnEmptyOptionalWhenUnmarshalDatePeriodTypeWithNoElement() {
    final var result = PrefillAnswer.unmarshalDatePeriodType(
        List.of("not-an-element"));
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldConvertXMLGregorianCalendarToLocalDate() throws Exception {
    final var xmlCal = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar("2024-06-01");
    final var localDate = PrefillAnswer.toLocalDate(xmlCal);
    assertEquals(LocalDate.of(2024, 6, 1), localDate);
  }

  @Test
  void shouldUnmarshalCVTypeCorrectly() throws Exception {
    final var cvType = new CVType();
    cvType.setCode("CODE123");
    cvType.setDisplayName("Display Name");
    final var factory = new ObjectFactory();
    final var element = getElement(cvType, factory::createCv);

    final var result = PrefillAnswer.unmarshalCVType(List.of(element));

    assertAll(
        () -> assertTrue(result.isPresent()),
        () -> assertEquals("CODE123", result.get().getCode()),
        () -> assertEquals("Display Name", result.get().getDisplayName())
    );
  }

  @Test
  void shouldUnmarshalDatePeriodTypeCorrectly() throws Exception {
    final var datePeriod = new DatePeriodType();
    final var start = DatatypeFactory.newInstance().newXMLGregorianCalendar("2024-01-01");
    final var end = DatatypeFactory.newInstance().newXMLGregorianCalendar("2024-12-31");
    datePeriod.setStart(start);
    datePeriod.setEnd(end);
    final var factory = new ObjectFactory();
    final var element = getElement(datePeriod, factory::createDatePeriod);

    final var result = PrefillAnswer.unmarshalDatePeriodType(List.of(element));

    assertAll(
        () -> assertTrue(result.isPresent()),
        () -> assertEquals(start, result.get().getStart()),
        () -> assertEquals(end, result.get().getEnd())
    );
  }

  private static <T> Element getElement(T object, Function<T, JAXBElement<T>> jaxbElementCreator)
      throws JAXBException, ParserConfigurationException, IOException, SAXException {
    final var jaxbElement = jaxbElementCreator.apply(object);

    final var context = JAXBContext.newInstance(object.getClass());
    final var marshaller = context.createMarshaller();
    final var writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);

    final var docFactory = DocumentBuilderFactory.newInstance();
    docFactory.setNamespaceAware(true);
    final var doc = docFactory.newDocumentBuilder()
        .parse(new InputSource(new StringReader(writer.toString())));
    return doc.getDocumentElement();
  }
}