package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller.unmarshalType;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.SubAnswersUtil;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillRadioMultipleCodeConverter implements PrefillStandardConverter {

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

    final var prefillErrors = new ArrayList<PrefillError>();
    prefillErrors.addAll(
        PrefillValidator.validateSingleAnswerOrSubAnswer(
            answers,
            subAnswers,
            specification
        )
    );

    prefillErrors.addAll(
        PrefillValidator.validateDelsvarId(
            SubAnswersUtil.get(answers, subAnswers),
            configurationRadioMultipleCode,
            specification
        )
    );

    if (!prefillErrors.isEmpty()) {
      return PrefillAnswer.builder()
          .errors(prefillErrors)
          .build();
    }

    try {
      final var content = getContent(subAnswers, answers,
          specification.configuration().id().value());
      final var cvType = unmarshalType(content, CVType.class);

      if (cvType.isEmpty()) {
        throw new IllegalStateException("Invalid format cvType is empty");
      }

      final var code = new Code(cvType.get().getCode(), cvType.get().getCodeSystem(),
          cvType.get().getDisplayName());

      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(
                      ElementValueCode.builder()
                          .codeId(getId(configurationRadioMultipleCode, code))
                          .code(code.code())
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
      Code code) {
    return configurationRadioMultipleCode.list()
        .stream()
        .filter(c -> c.code().matches(code))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(
                "Code not found: '%s'".formatted(code))
        )
        .id();
  }

  private static List<Object> getContent(List<Delsvar> subAnswers, List<Svar> answers, String id) {
    if (!subAnswers.isEmpty()) {
      return subAnswers.getFirst().getContent();
    }
    return answers
        .getFirst()
        .getDelsvar()
        .stream()
        .filter(subAnswer -> subAnswer.getId().equals(id))
        .toList()
        .getFirst()
        .getContent();
  }
}