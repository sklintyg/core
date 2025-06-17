package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.UNMARSHALL_ERROR;

import java.util.ArrayList;
import java.util.Collection;
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
    final var validationErrors = validateAnswer(answers, specification);

    final var answer = answers.stream()
        .findFirst()
        .orElseThrow();

    final var result = prefillSubAnswer(answer.getDelsvar(), specification);
    result.getErrors().addAll(validationErrors);
    return result;
  }


  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    final var validationErrors = validateSubAnswer(subAnswers, specification);
    final var result = PrefillAnswer.builder()
        .errors(validationErrors)
        .build();

    if (invalidSubAnswer(validationErrors)) {
      return result;
    }

    final var subAnswer = subAnswers.stream().findFirst().get();
    final var text = PrefillUnmarshaller.unmarshallString(
        subAnswer.getContent());

    if (text.isEmpty()) {
      result.getErrors()
          .add(PrefillError.unmarshallingError(specification.id().id(), subAnswer.getId()));
    } else {
      result.setElementData(ElementData.builder()
          .id(specification.id())
          .value(ElementValueText.builder()
              .textId(specification.configuration().id())
              .text(text.get())
              .build())
          .build());
    }

    return result;
  }

  private boolean invalidSubAnswer(List<PrefillError> validationErrors) {
    return validationErrors.stream().anyMatch(
        e -> e.type() == UNMARSHALL_ERROR || e.type() == PrefillErrorType.SUB_ANSWER_NOT_FOUND);
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

  private List<PrefillError> validateSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    final var errors = new ArrayList<PrefillError>();
    if (subAnswers.size() > 1) {
      errors.add(
          PrefillError.tooManySubAnswersFound(specification.id().id(), 1, subAnswers.size()));
    } else if (subAnswers.isEmpty()) {
      errors.add(PrefillError.subAnswersNotFound(specification.id().id()));
    }

    subAnswers.stream()
        .findFirst()
        .ifPresent(s -> {
          if (s.getContent().isEmpty()) {
            errors.add(new PrefillError(UNMARSHALL_ERROR,
                "Content not found in Delsvar %s for answer: %s".formatted(specification.id().id(),
                    s.getId())));
          }
        });

    return errors;
  }

  private Collection<PrefillError> validateAnswer(Collection<Svar> answers,
      ElementSpecification specification) {
    final var errors = new ArrayList<PrefillError>();
    if (answers.size() > 1) {
      errors.add(PrefillError.tooManyAnswersFound(specification.id().id(), 1, answers.size()));
    }
    return errors;
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
