package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillTextFieldConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationTextField.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationTextField configurationTextField)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    final var answers = prefill.getSvar().stream()
        .filter(svar -> svar.getId().equals(specification.id().id()))
        .toList();

    final var subAnswers = prefill.getSvar().stream()
        .map(Svar::getDelsvar)
        .flatMap(List::stream)
        .filter(delsvar -> delsvar.getId().equals(specification.id().id()))
        .toList();

    final var prefillError = PrefillValidator.validateSingleAnswerOrSubAnswer(
        answers,
        subAnswers,
        specification
    );

    if (prefillError != null) {
      return PrefillAnswer.builder()
          .errors(List.of(prefillError))
          .build();
    }

    final var noPrefill = subAnswers.isEmpty() && answers.isEmpty();
    return noPrefill
        ? null
        : PrefillAnswer.builder()
            .elementData(
                ElementData.builder()
                    .id(specification.id())
                    .value(
                        ElementValueText.builder()
                            .textId(configurationTextField.id())
                            .text(getContent(subAnswers, answers))
                            .build()
                    )
                    .build()
            )
            .build();
  }

  private static String getContent(List<Delsvar> subAnswers, List<Svar> answers) {
    if (!subAnswers.isEmpty()) {
      return (String) subAnswers.getFirst().getContent().getFirst();
    }
    return (String) answers.stream()
        .map(Svar::getDelsvar)
        .toList()
        .getFirst()
        .getFirst()
        .getContent()
        .getFirst();
  }
}