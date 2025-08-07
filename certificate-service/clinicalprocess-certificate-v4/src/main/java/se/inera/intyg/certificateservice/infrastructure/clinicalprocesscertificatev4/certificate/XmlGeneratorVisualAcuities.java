package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorVisualAcuities implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueVisualAcuities.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueVisualAcuities visualAcuities)) {
      return Collections.emptyList();
    }

    if (visualAcuities.isEmpty()) {
      return Collections.emptyList();
    }

    final var answer = new Svar();
    answer.setId(data.id().id());

    buildDelsvarForOcualarAcuity(visualAcuities.rightEye()).ifPresent(
        value -> answer.getDelsvar().addAll(value)
    );

    buildDelsvarForOcualarAcuity(visualAcuities.leftEye()).ifPresent(
        value -> answer.getDelsvar().addAll(value)
    );

    buildDelsvarForOcualarAcuity(visualAcuities.binocular()).ifPresent(
        value -> answer.getDelsvar().addAll(value)
    );

    return List.of(answer);
  }

  private Optional<List<Delsvar>> buildDelsvarForOcualarAcuity(VisualAcuity visualAcuity) {
    if (visualAcuity.withoutCorrection().value() == null
        && visualAcuity.withCorrection().value() == null) {
      return Optional.empty();
    }

    final var subAnswers = new ArrayList<Delsvar>();

    if (visualAcuity.withoutCorrection().value() != null) {
      final var subAnswer = new Delsvar();
      subAnswer.setId(visualAcuity.withoutCorrection().id().value());
      subAnswer.getContent().add(formatVisualAcuity(visualAcuity.withoutCorrection().value()));
      subAnswers.add(subAnswer);
    }

    if (visualAcuity.withCorrection().value() != null) {
      final var subAnswer = new Delsvar();
      subAnswer.setId(visualAcuity.withCorrection().id().value());
      subAnswer.getContent().add(formatVisualAcuity(visualAcuity.withCorrection().value()));
      subAnswers.add(subAnswer);
    }

    return Optional.of(subAnswers);
  }

  private String formatVisualAcuity(Double value) {
    return String.valueOf(value).replace(".", ",");
  }
}