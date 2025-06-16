package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Data
@Builder
public class PrefillAnswer {

  @JsonIgnore
  private ElementData elementData;
  @JsonIgnore
  private ElementId elementId;
  private Collection<PrefillError> errors;

  public static PrefillAnswer answerNotFound(String id) {
    return PrefillAnswer.builder()
        .elementId(new ElementId(id))
        .errors(List.of(PrefillError.answerNotFound(id)))
        .build();
  }

  public static PrefillAnswer subAnswerNotFound(String answerId, String sunAnswerId) {
    return PrefillAnswer.builder()
        .elementId(new ElementId(sunAnswerId))
        .errors(List.of(PrefillError.subAnswerNotFound(answerId, sunAnswerId)))
        .build();
  }
}
