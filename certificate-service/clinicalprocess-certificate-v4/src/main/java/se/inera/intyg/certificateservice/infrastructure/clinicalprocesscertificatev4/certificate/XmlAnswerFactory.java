package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public class XmlAnswerFactory {

  private XmlAnswerFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Svar> createAnswerFromString(ElementId id, FieldId fieldId, String value) {
    final var answer = new Svar();
    final var subAnswer = new Delsvar();

    answer.setId(id.id());
    subAnswer.setId(fieldId.value());
    subAnswer.getContent().add(value);

    answer.getDelsvar().add(subAnswer);
    return List.of(answer);
  }

}
