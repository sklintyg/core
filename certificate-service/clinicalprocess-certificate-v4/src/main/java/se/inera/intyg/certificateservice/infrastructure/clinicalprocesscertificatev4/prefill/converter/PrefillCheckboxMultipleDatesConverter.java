package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillError;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillUnmarshaller;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillCheckboxMultipleDatesConverter implements PrefillStandardConverter {

  private static final int MINIMUM_SUB_ANSWERS = 1;

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationCheckboxMultipleDate.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification, Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationCheckboxMultipleDate configurationCheckboxMultipleDate)) {
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
    final var elementData = ElementData.builder()
        .id(specification.id())
        .value(ElementValueDateList.builder()
            .dateListId(configurationCheckboxMultipleDate.id())
            .dateList(answers.stream()
                .map(s -> {
                  try {
                    final var validationError = PrefillValidator.validateMinimumNumberOfDelsvar(
                        s,
                        MINIMUM_SUB_ANSWERS,
                        specification
                    );

                    if (!validationError.isEmpty()) {
                      prefillErrors.addAll(validationError);
                      return null;
                    }
                    var code = getCode(s.getDelsvar(), specification);
                    var date = getDate(s, specification);
                    return ElementValueDate.builder()
                        .dateId(getCheckboxDate(configurationCheckboxMultipleDate, code).id())
                        .date(date)
                        .build();
                  } catch (Exception ex) {
                    prefillErrors.add(PrefillError.invalidFormat(s.getId(), ex.getMessage()));
                    return null;
                  }
                })
                .filter(Objects::nonNull)
                .toList())
            .build())
        .build();

    return PrefillAnswer.builder()
        .elementData(elementData)
        .errors(prefillErrors)
        .build();
  }

  private CheckboxDate getCheckboxDate(ElementConfigurationCheckboxMultipleDate configuration,
      Code code) {
    return configuration.dates()
        .stream()
        .filter(d -> d.code().matches(code))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Could not find a matching code for " + code));
  }

  private LocalDate getDate(Svar s, ElementSpecification specification) {
    final var subAnswer = s.getDelsvar().stream()
        .filter(d -> d.getId().equals(specification.id().id() + ".2"))
        .findFirst();

    if (subAnswer.isEmpty() || subAnswer.get().getContent().isEmpty()) {
      return LocalDate.now(ZoneId.systemDefault());
    }

    return LocalDate.parse((String) subAnswer.get().getContent().getFirst());
  }

  private Code getCode(List<Delsvar> subAnswers, ElementSpecification specification) {
    final var subAnswer = subAnswers.stream()
        .filter(d -> d.getId().equals(specification.id().id() + ".1"))
        .findFirst();

    if (subAnswer.isEmpty()) {
      throw new IllegalStateException("Invalid format: code value is missing");
    }

    final var cvType = PrefillUnmarshaller.cvType(subAnswer.get().getContent());

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format: cvType is empty");
    }

    var cv = cvType.get();
    return new Code(cv.getCode(), cv.getCodeSystem(), cv.getDisplayName());
  }
}