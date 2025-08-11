package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
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
public class PrefillCheckboxDateRangeListConverter implements PrefillStandardConverter {

  private static final int MINIMUM = 2;

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationCheckboxDateRangeList.class;
  }

  @Override
  public PrefillAnswer prefillAnswer(ElementSpecification specification, Forifyllnad prefill) {
    if (!(specification.configuration() instanceof ElementConfigurationCheckboxDateRangeList configurationCheckboxDateRangeList)) {
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
        .value(ElementValueDateRangeList.builder()
            .dateRangeListId(configurationCheckboxDateRangeList.id())
            .dateRangeList(answers.stream()
                .map(svar -> {
                  try {

                    final var minimumNumberOfDelsvarError = PrefillValidator.validateMinimumNumberOfDelsvar(
                        answers,
                        MINIMUM,
                        specification
                    );

                    if (!minimumNumberOfDelsvarError.isEmpty()) {
                      prefillErrors.addAll(minimumNumberOfDelsvarError);
                      return null;
                    }

                    final var dateContent = getDateContent(svar, specification);
                    final var datePeriodAnswer = PrefillUnmarshaller.datePeriodType(
                        dateContent
                    );

                    final var code = getCode(svar.getDelsvar(), specification);
                    final var codeConfiguration = getCodeConfiguration(
                        configurationCheckboxDateRangeList,
                        code
                    );

                    return DateRange.builder()
                        .dateRangeId(codeConfiguration.id())
                        .from(
                            PrefillUnmarshaller.toLocalDate(
                                datePeriodAnswer.orElseThrow().getStart())
                        )
                        .to(
                            PrefillUnmarshaller.toLocalDate(
                                datePeriodAnswer.orElseThrow().getEnd())
                        )
                        .build();
                  } catch (Exception ex) {
                    prefillErrors.add(PrefillError.invalidFormat(svar.getId(), ex.getMessage()));
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

  private ElementConfigurationCode getCodeConfiguration(
      ElementConfigurationCheckboxDateRangeList configuration,
      Code code) {
    return configuration.dateRanges()
        .stream()
        .filter(d -> d.code().matches(code))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Could not find a matching code for " + code));
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

  private static List<Object> getDateContent(Svar answer, ElementSpecification specification) {
    return answer.getDelsvar()
        .stream()
        .filter(d -> d.getId().equals(specification.id().id() + ".2"))
        .findFirst()
        .orElseThrow()
        .getContent();
  }
}