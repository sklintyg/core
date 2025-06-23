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
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillUnmarshallerTest {

  @Test
  void shouldReturnEmptyOptionalWhenUnmarshalTypeWithNoElement() {
    final var result = PrefillUnmarshaller.cvType(List.of("not-an-element"));
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnEmptyOptionalWhenUnmarshalDatePeriodTypeWithNoElement() {
    final var result = PrefillUnmarshaller.datePeriodType(
        List.of("not-an-element"));
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldConvertXMLGregorianCalendarToLocalDate() throws Exception {
    final var xmlCal = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar("2024-06-01");
    final var localDate = PrefillUnmarshaller.toLocalDate(xmlCal);
    assertEquals(LocalDate.of(2024, 6, 1), localDate);
  }

  @Test
  void shouldUnmarshalCvTypeCorrectly() throws Exception {
    final var cvType = new CVType();
    cvType.setCode("CODE123");
    cvType.setDisplayName("Display Name");
    final var factory = new ObjectFactory();
    final var element = getElement(cvType, factory::createCv);

    final var result = PrefillUnmarshaller.cvType(List.of(element));

    assertAll(
        () -> assertTrue(result.isPresent()),
        () -> assertEquals("CODE123", result.get().getCode()),
        () -> assertEquals("Display Name", result.get().getDisplayName())
    );
  }

  @Test
  void shouldUnmarshalForifyllnadType() throws Exception {
    final var forifyllnad = new Forifyllnad();
    final var svar = new Svar();
    final var delsvar = new Delsvar();
    svar.setId("1");
    svar.setInstans(2);
    delsvar.setId("1.1");
    svar.getDelsvar().add(delsvar);
    forifyllnad.getSvar().add(svar);

    final var jaxbContext = jakarta.xml.bind.JAXBContext.newInstance(Forifyllnad.class, Svar.class,
        Delsvar.class);
    final var marshaller = jaxbContext.createMarshaller();
    final var writer = new StringWriter();
    final var qName = new javax.xml.namespace.QName(
        "urn:riv:clinicalprocess:healthcond:certificate:3.3", "forifyllnad");
    final var jaxbElement = new jakarta.xml.bind.JAXBElement<>(qName, Forifyllnad.class,
        forifyllnad);
    marshaller.marshal(jaxbElement, writer);
    final var xml = writer.toString();

    final var result = PrefillUnmarshaller.forifyllnadType(xml);

    final var answers = result.get().getSvar();
    final var subAnswers = answers.getFirst().getDelsvar();
    assertAll(
        () -> assertEquals(1, answers.size()),
        () -> assertEquals("1", answers.getFirst().getId()),
        () -> assertEquals(2, answers.getFirst().getInstans()),
        () -> assertEquals(1, subAnswers.size()),
        () -> assertEquals("1.1", subAnswers.getFirst().getId())
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