package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import java.util.List;
import java.util.Objects;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public class XmlGeneratorValue {

  public List<Svar> generate(List<ElementData> elementData) {
    return elementData.stream()
        .map(data -> {
          if (!(data.value() instanceof ElementValueDate dateValue)) {
            return null;
          }

          if (dateValue.date() == null) {
            return null;
          }

          final var answer = new Svar();
          final var subAnswer = new Delsvar();

          answer.setId(data.id().id());
          subAnswer.setId(dateValue.dateId().value());
          subAnswer.getContent().add(dateValue.date().toString());

          answer.getDelsvar().add(subAnswer);

          return answer;
        })
        .filter(Objects::nonNull)
        .toList();
  }

}
