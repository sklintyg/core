package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public interface PrefillConverter {

  Class<? extends ElementConfiguration> supports();

  PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification);

  PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification);

  default Collection<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    if (!model.elementSpecificationExists(new ElementId(answer.getId()))) {
      return List.of(PrefillAnswer.answerNotFound(answer.getId()));
    }

    return answer.getDelsvar().stream()
        .filter(subAnswerIdNotInModel(new ElementId(answer.getId()), model))
        .map(subAnswer -> PrefillAnswer.subAnswerNotFound(answer.getId(), subAnswer.getId()))
        .toList();
  }

  private static Predicate<Delsvar> subAnswerIdNotInModel(ElementId answerId,
      CertificateModel model) {
    return subAnswer -> !model.elementSpecification(answerId)
        .configuration()
        .id()
        .value()
        .equals(subAnswer.getId());
  }
}
