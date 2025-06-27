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
    prefilledAnswers.addAll(prefillHandler.unknownAnswerIds(certificateModel, prefill));
    prefilledAnswers.addAll(prefillHandler.handlePrefill(certificateModel, prefill));
  }

  public Set<ElementData> prefilledElements() {
    return prefilledAnswers.stream()
        .map(PrefillAnswer::getElementData)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }

  public String toJsonReport() {
    if (!containsError()) {
      return "Success";
    }
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

  public boolean containsError() {
    return prefilledAnswers.stream()
        .flatMap(e -> e.getErrors().stream())
        .anyMatch(Objects::nonNull);
  }
}