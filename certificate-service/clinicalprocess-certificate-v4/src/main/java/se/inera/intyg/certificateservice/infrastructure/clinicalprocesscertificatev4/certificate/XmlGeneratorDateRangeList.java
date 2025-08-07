package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorDateRangeList implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueDateRangeList.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueDateRangeList dateRangeListValue)) {
      return Collections.emptyList();
    }

    if (dateRangeListValue.dateRangeList() == null
        || dateRangeListValue.dateRangeList().isEmpty()) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationCheckboxDateRangeList configuration)) {
      throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(specification.configuration().getClass())
      );
    }

    final var objectFactory = new ObjectFactory();

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
              final var convertedDateRangeType = objectFactory.createDatePeriod(dateRangeType);
              subAnswerDateRange.getContent().add(convertedDateRangeType);

              final var code = configuration.code(dateRange);
              subAnswerCode.setId(getCvId(data.id().id()));
              cvType.setCode(code.code());
              cvType.setCodeSystem(code.codeSystem());
              cvType.setDisplayName(code.displayName());

              final var convertedCvType = objectFactory.createCv(cvType);
              subAnswerCode.getContent().add(convertedCvType);

              dateRangeAnswer.getDelsvar().add(subAnswerCode);
              dateRangeAnswer.getDelsvar().add(subAnswerDateRange);

              return dateRangeAnswer;
            }
        ).toList();
  }

  private static String getDateRangeId(String id) {
    return id + ".2";
  }

  private static String getCvId(String id) {
    return id + ".1";
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