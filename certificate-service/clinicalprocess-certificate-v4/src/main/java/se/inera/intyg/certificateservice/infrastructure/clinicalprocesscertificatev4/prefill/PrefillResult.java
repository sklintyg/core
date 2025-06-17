package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Data
@Builder
@Slf4j
public class PrefillResult {

  private CertificateModel certificateModel;
  private PrefillHandler prefillHandler;
  private List<PrefillAnswer> prefilledAnswers;
  private Forifyllnad prefill;

  public static PrefillResult create(CertificateModel certificateModel,
      Forifyllnad unmarshalledPrefill, PrefillHandler prefillHandler) {
    return PrefillResult.builder()
        .certificateModel(certificateModel)
        .prefillHandler(prefillHandler)
        .prefill(unmarshalledPrefill)
        .prefilledAnswers(new ArrayList<>()).build();
  }
  
  public void prefill() {
    addErrorForUnknownAnswerIds();
    prefillAnswers();
    prefillSubAnswers();
  }

  private void addErrorForUnknownAnswerIds() {
    prefill.getSvar().forEach(answer ->
        prefilledAnswers.addAll(prefillHandler.unknownAnswerIds(answer,
            certificateModel)
        )
    );
  }

  private void prefillSubAnswers() {

    prefill.getSvar().forEach(
        answer -> {
          final var subAnswers = answer.getDelsvar().stream()
              .filter(subAnswer -> certificateModel.elementSpecificationExists(
                  new ElementId(subAnswer.getId())))
              .collect(Collectors.groupingBy(Svar.Delsvar::getId));

          subAnswers.forEach((key, value) -> prefilledAnswers.add(
              prefillHandler.prefillSubAnswer(value,
                  certificateModel.elementSpecification(new ElementId(key)))
          ));
        });
  }

  private void prefillAnswers() {
    prefilledAnswers.addAll(prefill.getSvar().stream()
        .filter(
            answer -> certificateModel.elementSpecificationExists(new ElementId(answer.getId())))
        .collect(Collectors.groupingBy(Svar::getId))
        .entrySet().stream()
        .map(entry -> prefillHandler.prefillAnswer(entry.getValue(),
            certificateModel.elementSpecification(new ElementId(entry.getKey()))))
        .toList()
    );

  }

  public Set<ElementData> prefilledElements() {
    return prefilledAnswers.stream()
        .map(PrefillAnswer::getElementData)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
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
