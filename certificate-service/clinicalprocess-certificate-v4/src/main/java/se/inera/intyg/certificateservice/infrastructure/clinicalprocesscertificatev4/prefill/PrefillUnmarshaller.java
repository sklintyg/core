package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import jakarta.xml.bind.JAXBContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.xml.datatype.XMLGregorianCalendar;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;

public final class PrefillUnmarshaller {

  private PrefillUnmarshaller() {
  }

  public static Optional<String> unmarshallString(List<Object> content) {
    try {
      return Optional.of((String) content.getFirst());
    } catch (Exception e) {
      return Optional.empty();
    }
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

  public static Optional<CVType> unmarshalCVType(List<Object> content) {
    return unmarshalType(content, CVType.class);
  }

  public static Optional<DatePeriodType> unmarshalDatePeriodType(List<Object> content) {
    return unmarshalType(content, DatePeriodType.class);
  }

  public static LocalDate toLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
    return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
  }

}
