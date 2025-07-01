package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller.unmarshalType;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillRadioMultipleCodeConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationRadioMultipleCode.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationRadioMultipleCode configurationRadioMultipleCode)) {
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

    if (subAnswers.isEmpty() && answers.isEmpty()) {
      return null;
    }

    final var prefillError = PrefillValidator.validateSingleAnswerOrSubAnswer(
        answers,
        subAnswers,
        specification
    );

    if (!prefillError.isEmpty()) {
      return PrefillAnswer.builder()
          .errors(prefillError)
          .build();
    }

    try {
      final var content = getContent(subAnswers, answers);
      final var cvType = unmarshalType(content, CVType.class);

      final var code = cvType.orElseThrow().getCode();

      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(ElementValueCode.builder()
                      .codeId(getId(configurationRadioMultipleCode, code))
                      .code(code)
                      .build()
                  )
                  .build()
          )
          .build();

    } catch (Exception ex) {
      return PrefillAnswer.invalidFormat(specification.id().id(), ex.getMessage());
    }
  }

  private static FieldId getId(ElementConfigurationRadioMultipleCode configurationRadioMultipleCode,
      String code) {
    return configurationRadioMultipleCode.list()
        .stream()
        .filter(c -> c.code().code().equals(code))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(
                "Code not found: '%s'".formatted(code))
        )
        .id();
  }

  private static List<Object> getContent(List<Delsvar> subAnswers, List<Svar> answers) {
    if (!subAnswers.isEmpty()) {
      return subAnswers.getFirst().getContent();
    }
    return answers
        .getFirst()
        .getDelsvar()
        .getFirst()
        .getContent();
  }
}