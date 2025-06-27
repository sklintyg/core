package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.TestMarshaller.getElement;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import org.junit.jupiter.api.Test;
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
  void shouldUnmarshalCvTypeCorrectly() {
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


}