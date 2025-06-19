package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class PrefillRadioMultipleCodeConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationRadioMultipleCode.class;
  }

  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationRadioMultipleCode configurationRadioMultipleCode)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (subAnswers.size() != 1) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongNumberOfAnswers(1, subAnswers.size())))
          .build();
    }

    String code;
    FieldId id;
    try {
      final var cvType = PrefillUnmarshaller.cvType(subAnswers.getFirst().getContent());

      if (cvType.isEmpty()) {
        return PrefillAnswer.invalidFormat();
      }

      code = cvType.get().getCode();
      id = configurationRadioMultipleCode.list()
          .stream()
          .filter(c -> c.code().code().equals(code))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Code not found: " + code))
          .id();
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }

    return PrefillAnswer.builder()
        .elementData(
            ElementData.builder()
                .id(specification.id())
                .value(ElementValueCode.builder()
                    .codeId(id)
                    .code(code)
                    .build()
                ).build()
        ).build();
  }

  public PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationRadioMultipleCode)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (answers.size() != 1) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongNumberOfAnswers(1, answers.size())))
          .build();
    }

    return prefillSubAnswer(answers.getFirst().getDelsvar(), specification);
  }

  @Override
  public List<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    return Collections.emptyList();
  }
}
