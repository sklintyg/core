package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class PrefillConfigurationDiagnosis implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationDiagnosis.class;
  }

  @Override
  public PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    return null;
  }

  public PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification) {

    if (!(specification.configuration() instanceof ElementConfigurationDiagnosis)) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongConfigurationType()))
          .build();
    }

    if (answers.isEmpty()) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.wrongNumberOfAnswers(1, answers.size())))
          .build();
    }
    
    return PrefillAnswer.builder()
        .errors(List.of(PrefillError.unmarshallingError("", "")))
        .build();

//    final var diagnosisList = new ArrayList<ElementValueDiagnosis>();
//    try {
//      diagnosisList.addAll(answers.stream()
//          .map(answer -> getDiagnosis(answer.getDelsvar(), specification))
//          .toList());
//    } catch (Exception e) {
//      return PrefillAnswer.invalidFormat();
//    }
//
//    return PrefillAnswer.builder()
//        .elementData(
//            ElementData.builder()
//                .id(specification.id())
//                .value(ElementValueDiagnosisList.builder()
//                    .diagnoses(diagnosisList)
//                    .build()
//                ).build()
//        ).build();
  }

  private ElementValueDiagnosis getDiagnosis(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    return null;
  }

  @Override
  public List<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    return Collections.emptyList();
  }
}
