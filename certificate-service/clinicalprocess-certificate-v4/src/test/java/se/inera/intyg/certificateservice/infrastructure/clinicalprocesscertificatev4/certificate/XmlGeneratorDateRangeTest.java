package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBElement;
import java.time.LocalDate;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorDateRangeTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final LocalDate FROM = LocalDate.now().minusDays(1);
  private static final LocalDate TO = LocalDate.now();

  @InjectMocks
  private XmlGeneratorDateRange xmlGenerator;

  private ElementData data;
  private ElementSpecification elementSpecification;

  @BeforeEach
  void setup() {
    data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(ElementValueDateRange.builder()
            .id(new FieldId("ID"))
            .fromDate(FROM)
            .toDate(TO)
            .build())
        .build();

    elementSpecification = ElementSpecification.builder().build();
  }

  @Test
  void shouldMapSvar() {
    final var response = xmlGenerator.generate(data, elementSpecification);

    final var first = response.getFirst();
    assertAll(
        () -> assertEquals(1, response.size()),
        () -> assertEquals(QUESTION_ID, first.getId())
    );
  }

  @Test
  void shouldMapDelsvarForDateRange() {
    final var response = xmlGenerator.generate(data, elementSpecification);

    final var delsvar = response.getFirst().getDelsvar().getFirst();
    final var jaxbElement = (JAXBElement<DatePeriodType>) delsvar.getContent().getFirst();
    final var dateRange = jaxbElement.getValue();

    assertAll(
        () -> assertEquals(FROM, toLocalDate(dateRange.getStart())),
        () -> assertEquals(TO, toLocalDate(dateRange.getEnd()))
    );
  }

  @Test
  void shouldMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(ElementValueDateRange.builder().build())
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldMapEmptyIfValueIsNotDateRange() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(null)
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  private static LocalDate toLocalDate(XMLGregorianCalendar xmlFormat) {
    return LocalDate.of(
        xmlFormat.getYear(),
        xmlFormat.getMonth(),
        xmlFormat.getDay());
  }
}
