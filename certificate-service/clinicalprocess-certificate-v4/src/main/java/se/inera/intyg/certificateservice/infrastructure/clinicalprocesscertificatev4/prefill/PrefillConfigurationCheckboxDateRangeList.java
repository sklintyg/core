package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.time.Period;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
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

    if (answers.isEmpty()) {
      return null;
    }

    ElementData elementData = null;

    try {
      elementData = ElementData.builder()
          .id(specification.id())
          .value(null)
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

  private String getName(List<Delsvar> subAnswers, ElementSpecification specification) {
    return null;
  }

  private String getLabel(List<Delsvar> subAnswers, ElementSpecification specification) {
    return null;
  }

  private String getMessages(List<Delsvar> subAnswers, ElementSpecification specification) {
    return null;
  }

  private boolean getHideWorkingHours(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    return false;
  }

  private List<ElementConfigurationCode> getDateRanges(List<Delsvar> subAnswers,
      ElementSpecification specification) {
    return null;
  }

  private Period getMin(List<Delsvar> subAnswers, ElementSpecification specification) {
    return null;
  }

  private Period getMax(List<Delsvar> subAnswers, ElementSpecification specification) {
    return null;
  }
}
