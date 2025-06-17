package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class PrefillTextAreaConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationTextArea.class;
  }

  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationTextArea configurationTextArea)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (subAnswers.size() != 1) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongNumberOfAnswers(1, subAnswers.size())))
          .build();
    }

    return PrefillAnswer.builder()
        .elementData(
            ElementData.builder()
                .id(specification.id())
                .value(ElementValueText.builder()
                    .textId(configurationTextArea.id())
                    .text((String) subAnswers.getFirst().getContent().getFirst())
                    .build()
                ).build()
        ).build();
  }

  public PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification) {
    if (!(specification.configuration() instanceof ElementConfigurationTextArea)) {
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
