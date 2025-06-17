package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class PrefillRadioBooleanConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationRadioBoolean.class;
  }

  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationRadioBoolean configurationRadioBoolean)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (subAnswers.size() != 1) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongNumberOfAnswers(1, subAnswers.size())))
          .build();
    }

    boolean value;
    try {
      value = Boolean.parseBoolean((String) subAnswers.getFirst().getContent().getFirst());
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }

    return PrefillAnswer.builder()
        .elementData(
            ElementData.builder()
                .id(specification.id())
                .value(ElementValueBoolean.builder()
                    .booleanId(configurationRadioBoolean.id())
                    .value(value)
                    .build()
                ).build()
        ).build();
  }

  public PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationRadioBoolean)) {
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
