package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorDateRange implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueDateRange.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueDateRange dateRangeValue)) {
      return Collections.emptyList();
    }

    if (dateRangeValue.toDate() == null && dateRangeValue.fromDate() == null) {
      return Collections.emptyList();
    }

    final var objectFactory = new ObjectFactory();

    final var dateRangeAnswer = new Svar();
    dateRangeAnswer.setId(data.id().id());

    final var subAnswerDateRange = new Delsvar();
    final var dateRangeType = new DatePeriodType();

    subAnswerDateRange.setId(dateRangeValue.id().value());
    dateRangeType.setEnd(toXmlGregorianCalendar(dateRangeValue.toDate()));
    dateRangeType.setStart(toXmlGregorianCalendar(dateRangeValue.fromDate()));
    final var convertedDateRangeType = objectFactory.createDatePeriod(dateRangeType);
    subAnswerDateRange.getContent().add(convertedDateRangeType);

    dateRangeAnswer.getDelsvar().add(subAnswerDateRange);

    return List.of(dateRangeAnswer);
  }

  private XMLGregorianCalendar toXmlGregorianCalendar(LocalDate date) {
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