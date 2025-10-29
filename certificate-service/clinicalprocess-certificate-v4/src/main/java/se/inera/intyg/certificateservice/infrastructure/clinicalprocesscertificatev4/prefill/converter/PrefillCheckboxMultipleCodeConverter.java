package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

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
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillCheckboxMultipleCodeConverter implements PrefillStandardConverter {

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
        .filter(svar -> svar.getId().equals(getId(specification)))
        .toList();

    if (!specification.includeInXml()) {
      return PrefillAnswer.builder()
          .elementData(
              buildElementDataFromConfiguration(specification, configurationCheckboxMultipleCode)
          )
          .build();
    }

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
                            specification
                        );

                        if (!validationError.isEmpty()) {
                          prefillErrors.addAll(validationError);
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

  /**
   * Builds an {@link ElementData} containing all possible values from the configuration.
   * <p>
   * If {includeInXml} is {false}, we cannot prefill this value from the provided data. Therefore,
   * we prefill it with all values defined in the configuration.
   *
   * @param specification                     The element specification.
   * @param configurationCheckboxMultipleCode The configuration containing possible values.
   * @return An {@link ElementData} with all values from the configuration.
   */
  private static ElementData buildElementDataFromConfiguration(ElementSpecification specification,
      ElementConfigurationCheckboxMultipleCode configurationCheckboxMultipleCode) {
    return ElementData.builder()
        .id(specification.id())
        .value(
            ElementValueCodeList.builder()
                .id(configurationCheckboxMultipleCode.id())
                .list(
                    configurationCheckboxMultipleCode.list().stream()
                        .map(
                            configuration ->
                                ElementValueCode.builder()
                                    .codeId(configuration.id())
                                    .code(configuration.id().value())
                                    .build()
                        )
                        .toList()
                )
                .build()
        )

        .build();
  }

  private static String getId(ElementSpecification specification) {
    return specification.mapping() != null && specification.mapping().elementId() != null
        ? specification.mapping().elementId().id()
        : specification.id().id();
  }

  private static ElementValueCode getCodes(List<Delsvar> subAnswer,
      ElementConfigurationCheckboxMultipleCode configuration) {
    final var filteredSubAnswer = subAnswer.stream()
        .filter(delsvar -> delsvar.getId().equals(configuration.id().value()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Could not find delsvar with id: " + configuration.id().value()));

    final var cvType = PrefillUnmarshaller.cvType(filteredSubAnswer.getContent());

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