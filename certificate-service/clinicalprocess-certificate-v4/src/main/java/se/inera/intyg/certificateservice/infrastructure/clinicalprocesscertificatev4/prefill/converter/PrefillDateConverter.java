package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.SubAnswersUtil;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillDateConverter implements PrefillStandardConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationDate.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationDate configurationDate)) {
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

    if (answers.isEmpty() && subAnswers.isEmpty()) {
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
            configurationDate,
            specification
        )
    );

    if (!prefillErrors.isEmpty()) {
      return PrefillAnswer.builder()
          .errors(prefillErrors)
          .build();
    }

    try {
      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(
                      ElementValueDate.builder()
                          .dateId(configurationDate.id())
                          .date(LocalDate.parse(
                              SubAnswersUtil.getContent(subAnswers, answers, configurationDate)))
                          .build()
                  )
                  .build()
          )
          .build();
    } catch (Exception ex) {
      return PrefillAnswer.invalidFormat(specification.id().id(), ex.getMessage());
    }
  }
}