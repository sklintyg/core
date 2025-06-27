package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.extern.slf4j.Slf4j;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Slf4j
public final class PrefillUnmarshaller {

  private PrefillUnmarshaller() {
  }

  public static <T> Optional<T> unmarshalType(List<Object> content, Class<T> clazz) {
    final var contentObj = content.stream()
        .filter(obj -> obj instanceof org.w3c.dom.Element)
        .map(obj -> (org.w3c.dom.Element) obj)
        .findFirst();

    if (contentObj.isPresent()) {
      try {
        final var context = JAXBContext.newInstance(clazz);
        final var jaxbElement = context.createUnmarshaller()
            .unmarshal(contentObj.get(), clazz);
        return Optional.of(jaxbElement.getValue());
      } catch (Exception e) {
        throw new IllegalStateException("Failed to unmarshal " + clazz.getSimpleName(), e);
      }
    }
    return Optional.empty();
  }

  public static <T> Optional<T> unmarshalXml(String xml, Class<T> clazz) {
    try {
      final var context = JAXBContext.newInstance(clazz);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(xml);
      final var jaxbElement = (JAXBElement<T>) unmarshaller.unmarshal(stringReader);
      return Optional.of(jaxbElement.getValue());
    } catch (Exception ex) {
      log.warn("Failed to unmarshal {}", clazz.getSimpleName(), ex);
      return Optional.empty();
    }
  }

  public static Optional<CVType> cvType(List<Object> content) {
    return unmarshalType(content, CVType.class);
  }

  public static Optional<DatePeriodType> datePeriodType(List<Object> content) {
    return unmarshalType(content, DatePeriodType.class);
  }

  public static Optional<Forifyllnad> forifyllnadType(String xml) {
    return unmarshalXml(xml, Forifyllnad.class);
  }

  public static LocalDate toLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
    return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }

}