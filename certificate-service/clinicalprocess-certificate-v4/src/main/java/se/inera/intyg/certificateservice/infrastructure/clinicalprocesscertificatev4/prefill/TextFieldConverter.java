package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.UNMARSHALL_ERROR;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class TextFieldConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationTextField.class;
  }


  @Override
  public PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification) {
    return null;
  }

  @Override
  public List<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    if (!model.elementSpecificationExists(new ElementId(answer.getId()))) {
      return List.of(PrefillAnswer.answerNotFound(answer.getId()));
    }

    return answer.getDelsvar().stream()
        .filter(subAnswerIdNotInModel(new ElementId(answer.getId()), model))
        .map(subAnswer -> PrefillAnswer.subAnswerNotFound(answer.getId(), subAnswer.getId()))
        .toList();
  }

  private Predicate<Delsvar> subAnswerIdNotInModel(ElementId answerId, CertificateModel model) {
    return subAnswer -> !model.elementSpecification(answerId)
        .configuration()
        .id()
        .value()
        .equals(subAnswer.getId());
  }

  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    //TODO: handle empty subanswer and multiple subanswers?
    final var content = subAnswers.stream().findFirst().get().getContent();
    if (content != null && !content.isEmpty()) {
      var text = (String) content.getFirst();
      return PrefillAnswer.builder()
          .elementData(ElementData.builder()
              .id(specification.id())
              .value(ElementValueText.builder()
                  .textId(specification.configuration().id())
                  .text(text)
                  .build())
              .build())
          .build();
    }

    return PrefillAnswer.builder()
        .errors(List.of(new PrefillError(UNMARSHALL_ERROR,
            "Empty content in Delsvar for element: " + specification.id().id())))
        .build();
  }

}
