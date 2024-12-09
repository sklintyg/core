package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorOcularAcuities implements XmlGeneratorElementData {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueOcularAcuities.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueOcularAcuities ocularAcuities)) {
      return Collections.emptyList();
    }

    if (ocularAcuities.isEmpty()) {
      return Collections.emptyList();
    }

    final var answer = new Svar();
    answer.setId(data.id().id());

    buildDelsvarForOcualarAcuity(ocularAcuities.rightEye()).ifPresent(
        value -> answer.getDelsvar().addAll(value)
    );

    buildDelsvarForOcualarAcuity(ocularAcuities.leftEye()).ifPresent(
        value -> answer.getDelsvar().addAll(value)
    );

    buildDelsvarForOcualarAcuity(ocularAcuities.binocular()).ifPresent(
        value -> answer.getDelsvar().addAll(value)
    );

    return List.of(answer);
  }

  private Optional<List<Delsvar>> buildDelsvarForOcualarAcuity(OcularAcuity ocularAcuity) {
    if (ocularAcuity.withoutCorrection().value() == null
        && ocularAcuity.withCorrection().value() == null) {
      return Optional.empty();
    }

    final var subAnswers = new ArrayList<Delsvar>();

    if (ocularAcuity.withoutCorrection().value() != null) {
      final var subAnswer = new Delsvar();
      subAnswer.setId(ocularAcuity.withoutCorrection().id().value());
      subAnswer.getContent().add(ocularAcuity.withoutCorrection().value());
      subAnswers.add(subAnswer);
    }

    if (ocularAcuity.withCorrection().value() != null) {
      final var subAnswer = new Delsvar();
      subAnswer.setId(ocularAcuity.withCorrection().id().value());
      subAnswer.getContent().add(ocularAcuity.withCorrection().value());
      subAnswers.add(subAnswer);
    }

    return Optional.of(subAnswers);
  }
}