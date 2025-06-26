package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillCheckboxMultipleCodeConverter implements PrefillConverter {

  private static final int MINIMUM_SUB_ANSWERS = 1;

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

    if (answers.isEmpty()) {
      return null;
    }

    final var prefillErrors = new ArrayList<PrefillError>();
    final var data = ElementData.builder()
        .id(specification.id())
        .value(
            ElementValueCodeList.builder()
                .id(configurationCheckboxMultipleCode.id())
                .list(answers.stream()
                    .map(answer -> {
                      try {
                        final var validationError = PrefillValidator.validateMinimumNumberOfDelsvar(
                            answer,
                            MINIMUM_SUB_ANSWERS,
                            specification);

                        if (validationError != null) {
                          prefillErrors.add(validationError);
                          return null;
                        }
                        return getCodes(answer.getDelsvar(),
                            configurationCheckboxMultipleCode);
                      } catch (Exception e) {
                        prefillErrors.add(
                            PrefillError.invalidFormat(answer.getId(), e.getMessage()));
                        return null;
                      }
                    })
                    .filter(Objects::nonNull)
                    .toList())
                .build()
        )
        .build();

    return PrefillAnswer.builder()
        .elementData(data)
        .errors(prefillErrors)
        .build();
  }


  private static ElementValueCode getCodes(List<Delsvar> subAnswer,
      ElementConfigurationCheckboxMultipleCode configuration) {
    final var cvType = PrefillUnmarshaller.cvType(subAnswer.getFirst().getContent());

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format cvType is empty");
    }

    final var code = new Code(cvType.get().getCode(), cvType.get().getCodeSystem(),
        cvType.get().getDisplayName());

    final var listItem = configuration.list().stream()
        .filter(item -> item.code().matches(code))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Could not find a matching code for " + code));

    return ElementValueCode.builder()
        .codeId(listItem.id())
        .code(code.code())
        .build();
  }

}