package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;

@Data
@Builder
public class PrefillAnswer {

  @JsonIgnore
  private ElementData elementData;
  @Default
  private List<PrefillError> errors = new ArrayList<>();

  public static PrefillAnswer answerNotFound(String id) {
    return PrefillAnswer.builder()
        .errors(List.of(PrefillError.answerNotFound(id)))
        .build();
  }

  public static PrefillAnswer invalidFormat(String answerId, String exceptionMessage) {
    return PrefillAnswer.builder()
        .errors(List.of(PrefillError.invalidFormat(answerId, exceptionMessage)))
        .build();
  }
}