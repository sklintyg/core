package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillConfigurationCheckboxDateRangeList implements PrefillConverter {

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

    final var subAnswers = prefill.getSvar().stream()
        .map(Svar::getDelsvar)
        .flatMap(List::stream)
        .filter(delsvar -> delsvar.getId().equals(specification.id().id()))
        .toList();

    if (answers.isEmpty() && subAnswers.isEmpty()) {
      return null;
    }

    ElementData elementData;

    try {

      final var content = getContent(subAnswers, answers);
      final var datePeriodAnswer = PrefillUnmarshaller.datePeriodType(
          List.of(content)
      );

      elementData = ElementData.builder()
          .id(specification.id())
          .value(ElementValueDateRangeList.builder()
              .dateRangeListId(configurationCheckboxDateRangeList.id())
              .dateRangeList(answers.stream()
                  .map(svar -> {
                    final var code = getCode(svar.getDelsvar(), specification);
                    final var dateBox = getDateBox(configurationCheckboxDateRangeList, code);

                    return DateRange.builder()
                        .dateRangeId(dateBox.id())
                        .from(PrefillUnmarshaller.toLocalDate(datePeriodAnswer.get().getStart()))
                        .to(PrefillUnmarshaller.toLocalDate(datePeriodAnswer.get().getEnd()))
                        .build();
                  }).toList())
              .build())
          .build();

    } catch (Exception ex) {
      return PrefillAnswer.builder()
          .errors(List.of(PrefillError.invalidFormat(specification.id().id(), ex.getMessage())))
          .build();
    }

    return PrefillAnswer.builder()
        .elementData(elementData)
        .build();
  }

  private ElementConfigurationCode getDateBox(
      ElementConfigurationCheckboxDateRangeList configuration,
      Code code) {
    return configuration.dateRanges()
        .stream()
        .filter(d -> d.code().matches(code))
        .findFirst().orElseThrow();
  }

  private Code getCode(List<Delsvar> subAnswer, ElementSpecification specification) {
    final var sunAnswer = subAnswer.stream()
        .filter(d -> d.getId().equals(specification.id().id() + ".1"))
        .findFirst();

    if (sunAnswer.isEmpty()) {
      throw new IllegalStateException("Invalid format: code value is missing");
    }

    final var cvType = PrefillUnmarshaller.cvType(sunAnswer.get().getContent());

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format: cvType is empty");
    }

    var cv = cvType.get();
    return new Code(cv.getCode(), cv.getCodeSystem(), cv.getDisplayName());
  }

  private static Object getContent(List<Delsvar> subAnswers, List<Svar> answers) {
    if (!subAnswers.isEmpty()) {
      return subAnswers.getFirst().getContent().getFirst();
    }
    return answers.stream()
        .map(Svar::getDelsvar)
        .toList()
        .getFirst()
        .get(1)
        .getContent()
        .getFirst();
  }
}
