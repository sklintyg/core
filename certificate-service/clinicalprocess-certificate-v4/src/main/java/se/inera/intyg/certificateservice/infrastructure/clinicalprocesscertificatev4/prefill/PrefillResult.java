package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Data
@Builder
@Slf4j
public class PrefillResult {

  private CertificateModel certificateModel;
  private ConfigurationConverterMapper configurationConverterMapper;
  private List<PrefillAnswer> prefilledAnswers;
  private Forifyllnad prefill;

  public static PrefillResult prepare(CertificateModel certificateModel,
      Forifyllnad unmarshalledPrefill, ConfigurationConverterMapper configurationConverterMapper) {
    return PrefillResult.builder()
        .certificateModel(certificateModel)
        .configurationConverterMapper(configurationConverterMapper)
        .prefill(unmarshalledPrefill)
        .prefilledAnswers(new ArrayList<>()).build();
  }


  public void prefill() {
    prefillAnswers();
    prefillSubAnswers();
  }

  private void addErrorForUnknownAnswerIds() {
    prefilledAnswers.addAll(
        prefill.getSvar().stream()
            .filter(answerNotFoundInModel(certificateModel))
            .map(answer -> PrefillAnswer.answerNotFound(answer.getId()))
            .toList());
  }

  private void addErrorForUnknownSubAnswerIds() {
    prefill.getSvar().forEach(answer ->
        prefilledAnswers.addAll(
            answer.getDelsvar().stream()
                .filter(subAnswerNotFoundInModel(answer, certificateModel))
                .map(
                    subAnswer -> PrefillAnswer.subAnswerNotFound(answer.getId(), subAnswer.getId()))
                .toList())
    );
  }

  private Predicate<Delsvar> subAnswerNotFoundInModel(Svar answer,
      CertificateModel certificateModel) {
    return subAnswer -> {
      if (certificateModel.elementSpecificationExists(new ElementId(subAnswer.getId()))) {
        return false;
      }

      Collection<FieldId> ids = certificateModel.elementSpecification(new ElementId(answer.getId()))
          .configuration().availableSubAnswerIds(new ElementId(answer.getId()));

      return !ids.contains(new FieldId(subAnswer.getId()));
    };
  }

  private static Predicate<Svar> answerNotFoundInModel(CertificateModel certificateModel) {
    return answer -> !certificateModel.elementSpecificationExists(new ElementId(answer.getId()));
  }

  private void prefillSubAnswers() {
    addErrorForUnknownSubAnswerIds();

    prefilledAnswers.addAll(
        prefill.getSvar().stream()
            .flatMap(answer -> answer.getDelsvar().stream())
            .filter(subAnswer -> certificateModel.elementSpecificationExists(
                new ElementId(subAnswer.getId())))
            .map(subAnswer -> configurationConverterMapper.prefillSubAnswer(List.of(subAnswer),
                certificateModel.elementSpecification(new ElementId(subAnswer.getId()))))
            .toList()
    );

  }

  private void prefillAnswers() {
    addErrorForUnknownAnswerIds();

    prefilledAnswers.addAll(prefill.getSvar().stream()
        .filter(
            answer -> certificateModel.elementSpecificationExists(new ElementId(answer.getId())))
        .collect(Collectors.groupingBy(Svar::getId))
        .entrySet().stream()
        .map(entry -> configurationConverterMapper.prefillAnswer(entry.getValue(),
            certificateModel.elementSpecification(new ElementId(entry.getKey()))))
        .toList()
    );

  }

  public List<ElementData> prefilledElements() {
    return prefilledAnswers.stream()
        .map(PrefillAnswer::getElementData)
        .filter(Objects::nonNull)
        .toList();
  }

  public String toJsonReport() {
    try {
      return new ObjectMapper().writeValueAsString(
          this.prefilledAnswers.stream()
              .flatMap(prefillAnswer -> prefillAnswer.getErrors().stream())
              .filter(Objects::nonNull)
              .toList());
    } catch (JsonProcessingException e) {
      log.error("Failed to serialize PrefillResult to JSON", e);
      return "Exception occurred while serializing PrefillResult to JSON: " + e.getMessage();
    }
  }

}
