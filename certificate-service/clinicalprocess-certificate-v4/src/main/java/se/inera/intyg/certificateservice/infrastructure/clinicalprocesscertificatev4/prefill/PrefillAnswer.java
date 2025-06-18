package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.JAXBContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;

@Data
@Builder
public class PrefillAnswer {

  @JsonIgnore
  private ElementData elementData;
  @Default
  private List<PrefillError> errors = new ArrayList<>();

  public static PrefillAnswer answerNotFound(String id) {
    return PrefillAnswer.builder()
        .errors(List.of(PrefillError.answerNotFound(id)))
        .build();
  }

  public static PrefillAnswer subAnswerNotFound(String answerId, String sunAnswerId) {
    return PrefillAnswer.builder()
        .errors(List.of(PrefillError.subAnswerNotFound(answerId, sunAnswerId)))
        .build();
  }

  public static PrefillAnswer invalidFormat() {
    return PrefillAnswer.builder()
        .errors(List.of(PrefillError.invalidFormat()))
        .build();
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
