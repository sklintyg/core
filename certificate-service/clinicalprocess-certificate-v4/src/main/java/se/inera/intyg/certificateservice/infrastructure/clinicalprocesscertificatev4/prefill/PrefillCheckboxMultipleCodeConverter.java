package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillCheckboxMultipleCodeConverter implements PrefillConverter {

  private static final int LIMIT = 1;

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

    final var prefillError = PrefillValidator.validateNumberOfDelsvar(answers, LIMIT,
        specification);

    if (prefillError != null) {
      return PrefillAnswer.builder()
          .errors(List.of(prefillError))
          .build();
    }

    if (answers.isEmpty()) {
      return null;
    }

    try {
      final var codes = getContentCodes(answers, configurationCheckboxMultipleCode);
      return PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(specification.id())
                  .value(
                      ElementValueCodeList.builder()
                          .id(configurationCheckboxMultipleCode.id())
                          .list(codes)
                          .build()
                  )
                  .build()
          )
          .build();
    } catch (Exception e) {
      return PrefillAnswer.invalidFormat();
    }
  }

  private static List<ElementValueCode> getContentCodes(List<Svar> answers,
      ElementConfigurationCheckboxMultipleCode configuration) {
    return answers.stream()
        .map(svar -> getCodes(svar.getDelsvar(), configuration))
        .toList();
  }

  private static ElementValueCode getCodes(List<Delsvar> subAnswer,
      ElementConfigurationCheckboxMultipleCode configuration) {
    final var cvType = PrefillUnmarshaller.cvType(subAnswer.getFirst().getContent());

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format cvType is empty");
    }

    final var code = cvType.get().getCode();

    final var listItem = configuration.list().stream()
        .filter(item -> item.code().code().equals(code))
        .findFirst()
        .orElseThrow();

    return ElementValueCode.builder()
        .codeId(listItem.id())
        .code(code)
        .build();
  }

}