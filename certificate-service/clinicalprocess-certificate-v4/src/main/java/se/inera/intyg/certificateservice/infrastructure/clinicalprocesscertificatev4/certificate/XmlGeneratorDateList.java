package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorDateList implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueDateList.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueDateList dateListValue)) {
      return Collections.emptyList();
    }

    if (dateListValue.dateList() == null || dateListValue.dateList().isEmpty()) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationCheckboxMultipleDate configuration)) {
      throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(specification.configuration().getClass())
      );
    }

    final var objectFactory = new ObjectFactory();

    return dateListValue.dateList().stream()
        .map(date -> {
              final var dateAnswer = new Svar();
              dateAnswer.setId(data.id().id());
              dateAnswer.setInstans(dateListValue.dateList().indexOf(date) + 1);

              final var subAnswerDate = new Delsvar();
              final var subAnswerCode = new Delsvar();
              final var cvType = new CVType();

              subAnswerDate.setId(getDateId(data.id().id()));
              subAnswerDate.getContent().add(date.date().toString());

              final var code = configuration.code(date);
              subAnswerCode.setId(getCvId(data.id().id()));
              cvType.setCode(code.code());
              cvType.setCodeSystem(code.codeSystem());
              cvType.setDisplayName(code.displayName());

              final var convertedCvType = objectFactory.createCv(cvType);
              subAnswerCode.getContent().add(convertedCvType);

              dateAnswer.getDelsvar().add(subAnswerCode);
              dateAnswer.getDelsvar().add(subAnswerDate);

              return dateAnswer;
            }
        )
        .toList();
  }

  private static String getCvId(String id) {
    return id + ".1";
  }

  private static String getDateId(String id) {
    return id + ".2";
  }
}