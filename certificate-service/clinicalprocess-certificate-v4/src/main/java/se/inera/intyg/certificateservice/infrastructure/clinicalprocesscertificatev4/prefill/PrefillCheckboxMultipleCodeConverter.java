package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillCheckboxMultipleCodeConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationCheckboxMultipleCode.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification,
      Forifyllnad prefill) {

    if (!(specification.configuration() instanceof ElementConfigurationCheckboxMultipleCode configurationCheckboxMultipleCode)) {
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

    try {
      final var codes = getContent(subAnswers, answers, specification);
      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(ElementValueCodeList.builder()
                      .id(configurationCheckboxMultipleCode.id())
                      .list(codes)
                      .build()
                  ).build()
          ).build();
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }
  }

  private static List<ElementValueCode> getContent(List<Delsvar> subAnswers, List<Svar> answers,
      ElementSpecification specification) {
    if (!subAnswers.isEmpty()) {
      return List.of(getCode(subAnswers, specification));
    }
    return answers.stream()
        .map(svar -> getCode(svar.getDelsvar(), specification))
        .toList();
  }

  private static ElementValueCode getCode(List<Delsvar> subAnswer,
      ElementSpecification specification) {
    String code;
    final var cvType = PrefillUnmarshaller.cvType(subAnswer.getFirst().getContent());

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format cvType is empty");
    }

    code = cvType.get().getCode();
    final var configuration = (ElementConfigurationCheckboxMultipleCode) specification.configuration();

    final var listItem = configuration.list().stream()
        .filter(item -> item.code().code().equals(code))
        .findFirst().orElseThrow();

    return ElementValueCode.builder()
        .code(code)
        .codeId(listItem.id())
        .build();
  }

}
