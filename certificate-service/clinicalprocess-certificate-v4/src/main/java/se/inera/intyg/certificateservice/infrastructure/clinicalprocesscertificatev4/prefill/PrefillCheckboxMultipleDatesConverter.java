package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.util.PrefillValidator;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Component
public class PrefillCheckboxMultipleDatesConverter implements PrefillConverter {

  private static final int LIMIT = 2;

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

    var elementData = ElementData.builder()
        .id(specification.id())
        .value(ElementValueDateList.builder()
            .dateListId(configurationCheckboxMultipleDate.id())
            .dateList(answers.stream()
                .map(s -> {
                  var code = getCode(s.getDelsvar(), specification);
                  var date = getDate(s, specification);
                  return ElementValueDate.builder()
                      .dateId(getDateBox(specification, code).id())
                      .date(date)
                      .build();
                })
                .toList())
            .build())
        .build();

    PrefillAnswer.builder().elementData(elementData);
    return PrefillAnswer.builder()
        .elementData(elementData)
        .build();
  }

  private LocalDate getDate(Svar s, ElementSpecification specification) {
    return LocalDate.parse(((String) s.getDelsvar().stream()
        .filter(d -> d.getId().equals(specification.id().id() + ".2"))
        .findFirst()
        .get()
        .getContent()
        .getFirst()));
  }

  private CheckboxDate getDateBox(ElementSpecification specification, Code code) {
    return ((ElementConfigurationCheckboxMultipleDate) specification.configuration()).dates()
        .stream()
        .filter(d -> d.code().matches(
            code))
        .findFirst().orElseThrow();
  }

  @Override
  public List<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    if (!model.elementSpecificationExists(new ElementId(answer.getId()))) {
      return List.of(PrefillAnswer.answerNotFound(answer.getId()));
    }

    var result = new ArrayList<PrefillAnswer>();
    answer.getDelsvar().stream().filter(
            subAnswer -> !model.elementSpecificationExists(new ElementId(subAnswer.getId())))
        .forEach(subAnswer -> {
          if (!List.of("1.1", "1.2").contains(subAnswer.getId())) {
            result.add(PrefillAnswer.subAnswerNotFound(answer.getId(), subAnswer.getId()));
          }
        });

    return result;
  }

  private Code getCode(List<Delsvar> subAnswer, ElementSpecification specification) {
    final var cvType = PrefillUnmarshaller.cvType(subAnswer.stream()
        .filter(d -> d.getId().equals(specification.id().id() + ".1"))
        .findFirst().get().getContent());

    if (cvType.isEmpty()) {
      throw new IllegalStateException("Invalid format cvType is empty");
    }

    var cv = cvType.get();
    return new Code(cv.getCode(), cv.getCodeSystem(),
        cv.getDisplayName());
  }
}