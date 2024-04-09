package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public class XmlGeneratorDateRangeList implements XmlGeneratorElementData {

  public List<Svar> generate(ElementData data) {
    if (!(data.value() instanceof ElementValueDateRangeList dateRangeListValue)) {
      return Collections.emptyList();
    }

    if (dateRangeListValue.dateRangeList() == null
        || dateRangeListValue.dateRangeList().isEmpty()) {
      return Collections.emptyList();
    }

    return dateRangeListValue.dateRangeList().stream()
        .map(dateRange -> {
          final var dateRangeAnswer = new Svar();
          dateRangeAnswer.setId(data.id().id());
          dateRangeAnswer.setInstans(dateRangeListValue.dateRangeList().indexOf(dateRange) + 1);

          final var subAnswerDateRange = new Delsvar();
          final var dateRangeType = new DatePeriodType();

          final var subAnswerCode = new Delsvar();
          final var cvType = new CVType();

          subAnswerDateRange.setId(getDateRangeId(data.id().id()));
          dateRangeType.setEnd(toXmlGregorianCalendar(dateRange.to()));
          dateRangeType.setStart(toXmlGregorianCalendar(dateRange.from()));
          subAnswerDateRange.getContent().add(dateRangeType);

          subAnswerCode.setId(getCvId(data.id().id()));
          cvType.setCode(dateRange.dateRangeId().value());
          cvType.setCodeSystem("KV_FKMU_0003");
          cvType.setDisplayName(""); // TODO: We need displayname here, from config
          subAnswerCode.getContent().add(cvType);

          dateRangeAnswer.getDelsvar().add(subAnswerDateRange);
          dateRangeAnswer.getDelsvar().add(subAnswerCode);

          return dateRangeAnswer;
        }).toList();
  }

  private static String getDateRangeId(String id) {
    return id + ".1";
  }

  private static String getCvId(String id) {
    return id + ".2";
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
